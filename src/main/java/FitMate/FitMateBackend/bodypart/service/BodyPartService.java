package FitMate.FitMateBackend.bodypart.service;

import FitMate.FitMateBackend.bodypart.dto.BodyPartDto;
import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.bodypart.repository.BodyPartRepository;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BodyPartService {

    private final BodyPartRepository bodyPartRepository;


    public BodyPart findByKoreanName(String koreanName) {
        return bodyPartRepository.findByKoreanName(koreanName).orElse(null);
    }

    //Overloading
    public List<BodyPart> findAll() {
        return bodyPartRepository.findAll();
    }
    public ResponseEntity<?> findAll(int page) {
        List<BodyPart> bodyParts = bodyPartRepository.findAll(page);
        if(bodyParts.isEmpty())
            throw new CustomException(CustomErrorCode.PAGE_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(
                bodyParts.stream()
                .map(BodyPartDto::new)
                .collect(Collectors.toList()));
    }
    //Overloading
}
