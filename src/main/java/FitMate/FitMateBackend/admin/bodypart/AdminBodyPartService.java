package FitMate.FitMateBackend.admin.bodypart;

import FitMate.FitMateBackend.bodypart.dto.BodyPartResponse;
import FitMate.FitMateBackend.domain.BodyPart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBodyPartService {

    private final AdminBodyPartRepository adminBodyPartRepository;

    public PageImpl<BodyPartResponse> readAll(Pageable pageable) {
        Page<BodyPart> bodyPartList = adminBodyPartRepository.findAll(pageable);
        return new PageImpl<>(
            bodyPartList.stream().map(BodyPartResponse::new).toList(),
            pageable,
            bodyPartList.getTotalElements()
        );
    }
}
