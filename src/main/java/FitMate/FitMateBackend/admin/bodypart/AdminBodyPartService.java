package FitMate.FitMateBackend.admin.bodypart;

import FitMate.FitMateBackend.bodypart.dto.BodyPartResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.domain.BodyPart;
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

//    @Transactional
//    public ResponseEntity<String> saveBodyPart(BodyPartRequest request) {
//        if(!this.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
//            throw new CustomException(CustomErrorCode.BODY_PART_ALREADY_EXIST_EXCEPTION);
//
//        BodyPart bodyPart = new BodyPart();
//        bodyPart.update(request.getEnglishName(), request.getKoreanName());
//
//        bodyPartRepository.save(bodyPart);
//
//        return ResponseEntity.ok("[" + bodyPart.getKoreanName() + ":" + bodyPart.getEnglishName() + "] 등록 완료");
//    }

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

//    @Transactional
//    public ResponseEntity<String> removeBodyPart(Long bodyPartId) {
//        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId).orElse(null);
//        if(findBodyPart == null)
//            throw new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION);
//
//        //remove related machine
//        List<Machine> machines = findBodyPart.getMachines();
//        for (Machine machine : machines) {
//            machine.getBodyParts().remove(findBodyPart);
//        }
//
//        //remove related workout
//        List<Workout> workouts = findBodyPart.getWorkouts();
//        for (Workout workout : workouts) {
//            workout.getBodyParts().remove(findBodyPart);
//        }
//
//        bodyPartRepository.remove(findBodyPart);
//        return ResponseEntity.ok("[" + findBodyPart.getKoreanName() + ":" + findBodyPart.getEnglishName() + "] 삭제 완료");
//    }
}
