package FitMate.FitMateBackend.admin.bodypart;

import FitMate.FitMateBackend.bodypart.dto.BodyPartResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.common.exception.ApiErrorCode;
import FitMate.FitMateBackend.common.exception.ApiException;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminBodyPartService {

    private final AdminBodyPartRepository adminBodyPartRepository;

    @Transactional
    public void create(BodyPartRequest request) {
        Optional<BodyPart> duplicateBodyPart = adminBodyPartRepository.findByKoreanNameOrEnglishName(
            request.getKoreanName(), request.getEnglishName()
        );
        if(duplicateBodyPart.isPresent()) {
            throw new ApiException(ApiErrorCode.BODY_PART_ALREADY_EXIST_EXCEPTION);
        }

        BodyPart bodyPart = new BodyPart();
        bodyPart.update(request.getEnglishName(), request.getKoreanName());

        adminBodyPartRepository.save(bodyPart);
    }

    @Transactional
    public void updateBodyPart(Long bodyPartId, BodyPartRequest request) {
//        if(!this.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
//            throw new CustomException(CustomErrorCode.BODY_PART_ALREADY_EXIST_EXCEPTION);
//
//        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId).orElse(null);
//        if(findBodyPart == null)
//            throw new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION);
//
//        findBodyPart.update(request.getEnglishName(), request.getKoreanName());
//        return ResponseEntity.ok("[" + findBodyPart.getKoreanName() + ":" + findBodyPart.getEnglishName() + "] 수정 완료");
    }

    public PageImpl<BodyPartResponse> readAll(Pageable pageable) {
        Page<BodyPart> bodyPartList = adminBodyPartRepository.findAll(pageable);
        return new PageImpl<>(
            bodyPartList.stream().map(BodyPartResponse::new).toList(),
            pageable,
            bodyPartList.getTotalElements()
        );
    }

    @Transactional
    public void delete(Long id) {
        BodyPart bodyPart = adminBodyPartRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION)
        );

        for (Machine machine : bodyPart.getMachines()) machine.removeBodyPart(bodyPart);
        for (Workout workout : bodyPart.getWorkouts()) workout.removeBodypart(bodyPart);

        adminBodyPartRepository.delete(bodyPart);
    }
}
