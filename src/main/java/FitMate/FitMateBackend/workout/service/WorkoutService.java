package FitMate.FitMateBackend.workout.service;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.dto.WorkoutResponseDto;
import java.util.List;

public interface WorkoutService {

    /**
     * Workout 생성
     * @param form Workout 생성 정보
     * @return 생성된 Workout 반환
     * */
    WorkoutResponseDto save(WorkoutForm form);

    /**
     * Workout 정보 수정
     * @param form Workout 수정 정보
     * @id 수정할 Workout의 id(PK)
     * @return 수정된 Workout 반환
     * */
    WorkoutResponseDto update(WorkoutForm form, Long id);

    /**
     * Workout 단건 조회 서비스
     * @param id 조회할 Workout의 id(PK)
     * @return 조회된 Workout 반환
     * */
    WorkoutResponseDto findById(Long id);

    /**
     * Workout 삭제
     * @param id 삭제할 Workout의 id(PK)
     * @return 삭제한 Workout 반환
     * */
    WorkoutResponseDto remove(Long id);

    /**
     * Workout 검색 서비스
     * @param page 검색 대상 페이지 번호
     * @param cond 검색 키워드 및 옵션 객체
     * @return 검색 조건에 부합하는 Workout 리스트 반환
     * */
    List<WorkoutResponseDto> searchAll(int page, WorkoutSearchCond cond);
}