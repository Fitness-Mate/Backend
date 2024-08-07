package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartDto;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartResponseDto;
import FitMate.FitMateBackend.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BodyPartService {

    private final BodyPartRepository bodyPartRepository;

    @Transactional
    public ResponseEntity<String> saveBodyPart(BodyPartRequest request) {
        if(!this.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            throw new CustomException(CustomErrorCode.BODY_PART_ALREADY_EXIST_EXCEPTION);

        BodyPart bodyPart = new BodyPart();
        bodyPart.update(request.getEnglishName(), request.getKoreanName());

        bodyPartRepository.save(bodyPart);

        return ResponseEntity.ok("[" + bodyPart.getKoreanName() + ":" + bodyPart.getEnglishName() + "] 등록 완료");
    }

    @Transactional
    public ResponseEntity<String> updateBodyPart(Long bodyPartId, BodyPartRequest request) {
        if(!this.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            throw new CustomException(CustomErrorCode.BODY_PART_ALREADY_EXIST_EXCEPTION);

        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId).orElse(null);
        if(findBodyPart == null)
            throw new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION);

        findBodyPart.update(request.getEnglishName(), request.getKoreanName());
        return ResponseEntity.ok("[" + findBodyPart.getKoreanName() + ":" + findBodyPart.getEnglishName() + "] 수정 완료");
    }

    public ResponseEntity<?> findOne(Long bodyPartId) {
        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId).orElse(null);
        if(findBodyPart == null)
            throw new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(new BodyPartResponseDto(findBodyPart));
    }

    public boolean checkBodyPartNameDuplicate(String koreanName, String englishName) {
        BodyPart bp1 = bodyPartRepository.findByKoreanName(koreanName).orElse(null);
        BodyPart bp2 = bodyPartRepository.findByEnglishName(englishName).orElse(null);
        return (bp1 == null && bp2 == null);
    }

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

    @Transactional
    public ResponseEntity<String> removeBodyPart(Long bodyPartId) {
        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId).orElse(null);
        if(findBodyPart == null)
            throw new CustomException(CustomErrorCode.BODY_PART_NOT_FOUND_EXCEPTION);

        //remove related machine
        List<Machine> machines = findBodyPart.getMachines();
        for (Machine machine : machines) {
            machine.getBodyParts().remove(findBodyPart);
        }

        //remove related workout
        List<Workout> workouts = findBodyPart.getWorkouts();
        for (Workout workout : workouts) {
            workout.getBodyParts().remove(findBodyPart);
        }

        bodyPartRepository.remove(findBodyPart);
        return ResponseEntity.ok("[" + findBodyPart.getKoreanName() + ":" + findBodyPart.getEnglishName() + "] 삭제 완료");
    }

}
