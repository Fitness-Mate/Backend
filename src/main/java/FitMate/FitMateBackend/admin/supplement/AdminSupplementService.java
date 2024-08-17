package FitMate.FitMateBackend.admin.supplement;

import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementFlavorDto;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRepository;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementService;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.exception.ApiErrorCode;
import FitMate.FitMateBackend.common.exception.ApiException;
import FitMate.FitMateBackend.common.util.S3Util;
import FitMate.FitMateBackend.supplement.dto.SupplementListResponse;
import FitMate.FitMateBackend.supplement.dto.SupplementRequest;
import FitMate.FitMateBackend.supplement.dto.SupplementResponse;
import FitMate.FitMateBackend.supplement.entity.AminoAcid;
import FitMate.FitMateBackend.supplement.entity.Gainer;
import FitMate.FitMateBackend.supplement.entity.Other;
import FitMate.FitMateBackend.supplement.entity.Protein;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.supplement.entity.SupplementType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSupplementService {

    private final AdminSupplementRepository adminSupplementRepository;
    private final SupplementService supplementService;

    private final SupplementRepository supplementRepository;

    private final S3Util s3Util;

    @Transactional
    public void create(SupplementRequest request) {

        /* 보조제 타입별 객체 생성 */
        Supplement supplement;
        if (request.getSupplementType() == SupplementType.AminoAcid) {
            supplement = new AminoAcid(request);
        } else if (request.getSupplementType() == SupplementType.Gainer) {
            supplement = new Gainer(request);
        }else if (request.getSupplementType() == SupplementType.Other) {
            supplement = new Other(request);
        } else {
            supplement = new Protein(request);
        }

        /* 이미지 등록 */
        if (request.getImage()!=null && !request.getImage().isEmpty()) {
            String newImage = s3Util.uploadImage(ServiceConst.S3_DIR_SUPPLEMENT, request.getImage());
            supplement.setImageName(newImage);
        }
        else {
            supplement.setImageName(ServiceConst.DEFAULT_IMAGE_NAME);
        }

        adminSupplementRepository.save(supplement);
        supplementService.updateSupplementString();
    }

    public PageImpl<SupplementListResponse> readAll(Pageable pageable) {
        Page<Supplement> supplementList = adminSupplementRepository.findAll(pageable);
        return new PageImpl<>(
            supplementList.stream().map(SupplementListResponse::new).toList(),
            pageable,
            supplementList.getTotalElements()
        );
    }

    public SupplementResponse read(Long id) {
        Supplement supplement = adminSupplementRepository.findById(id).orElseThrow(
            () -> new ApiException(ApiErrorCode.SUPPLEMENT_NOT_FOUND_EXCEPTION)
        );
        List<SupplementFlavorDto> otherFlavorList = supplementRepository.getSupplementLineup(supplement);
        return new SupplementResponse(supplement, otherFlavorList);
    }

    public void delete(Long id) {
        Supplement supplement = adminSupplementRepository.findById(id).orElseThrow(
            () -> new ApiException(ApiErrorCode.SUPPLEMENT_NOT_FOUND_EXCEPTION)
        );
        adminSupplementRepository.delete(supplement);
    }
}
