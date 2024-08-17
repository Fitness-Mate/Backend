package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementFlavorDto;
import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementSearchForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRepository;
import FitMate.FitMateBackend.supplement.dto.SupplementResponse;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SupplementService {
    private final SupplementRepository supplementRepository;

    private String supplementString = "";

    public Supplement findSupplementById(Long id) {
        return supplementRepository.findById(id);
    }

//
//    @Transactional
//    public Long updateSupplement(Long id, SupplementForm supplementForm) throws IOException {
//        Supplement supplement = supplementRepository.findById(id);
//        if (supplement == null) { // 바꾸고자 하는 대상이 없는 비정상 요청
//            return id;
//
//        } else { // 바꾸고자 하는 대상이 entity가 있는 정상상태
//
//            if (supplement.getType() == supplementForm.getSupplementType()) { // 같은 타입의 경우 (entity 신규 생성 불필요)
//
//                String oldImage = supplement.getImageName();
//                if (!oldImage.equals(ServiceConst.DEFAULT_IMAGE_NAME)) {
//                    // 이전 이미지 삭제
//                    s3Util.deleteImage(ServiceConst.S3_DIR_SUPPLEMENT, oldImage);
//                    log.info("deletedImage: " + oldImage);
//                }
////                String newImage = FileStoreService.storeFile(supplementForm.getImage());
//                // 새 이미지 등록
//                String newImage = s3Util.uploadImage(ServiceConst.S3_DIR_SUPPLEMENT, supplementForm.getImage());
//                supplement.setImageName(newImage); // 이미지 이름 등록
//                // 텍스트 정보 업데이트
//                if (supplement.getType() == SupplementType.AminoAcid) {
//                    AminoAcid sp = (AminoAcid)supplement;
//                    sp.updateFields(supplementForm);
//                    updateSupplementString();
//                    return id;
//                } else if (supplement.getType() == SupplementType.Gainer) {
//                    Gainer sp = (Gainer) supplement;
//                    sp.updateFields(supplementForm);
//                    updateSupplementString();
//                    return id;
//                } else if (supplement.getType() == SupplementType.Protein) {
//                    Protein sp = (Protein) supplement;
//                    sp.updateFields(supplementForm);
//                    updateSupplementString();
//                    return id;
//                }
//                log.error("update할 대상의 SupplementType 필드와 매칭되는 SupplementType이 없음");
//                return id;
//
//
//            }else { // 타입이 다른 경우 (기존 entity 삭제와 신규 Entity 생성 필요)
//
//                supplementRepository.deleteSupplement(id);
//                return createSupplement(supplementForm);
//
//            }
//        }
//    }

    public List<Supplement> getSupplementBatch(Long page) {
        return supplementRepository.getSupplementBatch(page);
    }

    public String getSupplementString() {
        if (supplementString.equals("")) {
            for (Supplement s : supplementRepository.getAllCaptains()) {
                supplementString = supplementString.concat(s.createIntroduction());
            }
        }
        return supplementString;
    }

    public void updateSupplementString() {
        supplementString = "";
        for (Supplement s : supplementRepository.getAllCaptains()) {
            supplementString = supplementString.concat(s.createIntroduction());
        }
    }

    public List<Supplement> searchSupplementBatch(Long page, SupplementSearchForm form){
        return supplementRepository.searchSupplement(page, form.getSupplementType(), form.getSearchKeyword());
    }

    public SupplementResponse makeSupplementDto(Supplement supplement) {
        List<SupplementFlavorDto> lineup = supplementRepository.getSupplementLineup(supplement);
        return new SupplementResponse(supplement, lineup);
    }
}
