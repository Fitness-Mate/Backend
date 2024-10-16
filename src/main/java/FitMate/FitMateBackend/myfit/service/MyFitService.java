package FitMate.FitMateBackend.myfit.service;

import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRepository;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.myfit.dto.mySupplement.MySupplementUpdateRequest;
import FitMate.FitMateBackend.myfit.dto.myWorkout.MyWorkoutUpdateRequest;
import FitMate.FitMateBackend.myfit.entity.MyFit;
import FitMate.FitMateBackend.myfit.entity.MySupplement;
import FitMate.FitMateBackend.myfit.entity.MyWorkout;
import FitMate.FitMateBackend.myfit.repository.MyFitRepository;
import FitMate.FitMateBackend.myfit.repository.RoutineRepository;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.workout.dto.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.entity.Workout;
import FitMate.FitMateBackend.workout.repository.WorkoutRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyFitService {

    private final MyFitRepository myFitRepository;
    private final RoutineRepository routineRepository;
    private final WorkoutRepository workoutRepository;
    private final SupplementRepository supplementRepository;

    @Transactional
    public String saveMyFit(MyFit myFit) {
        myFitRepository.save(myFit);
        return "[myFitId:" + myFit.getId() +"] 등록 완료";
    }

    public List<MyWorkout> findAllMyWorkoutWithRoutineId(Long routineId) {
        routineRepository.findById(routineId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ROUTINE_NOT_FOUND_EXCEPTION));

        return myFitRepository.findAllMyWorkoutWithRoutineId(routineId);
    }

    public List<MySupplement> findAllMySupplementWithRoutineId(Long routineId) {
        return myFitRepository.findAllMySupplementWithRoutineId(routineId);
    }

    public MyWorkout findMyWorkoutById(Long myWorkoutId) {
        return myFitRepository.findMyWorkoutById(myWorkoutId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MY_WORKOUT_NOT_FOUND_EXCEPTION));
    }

    public MySupplement findMySupplementById(Long mySupplementId) {
        return myFitRepository.findMySupplementById(mySupplementId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MY_SUPPLEMENT_NOT_FOUND_EXCEPTION));
    }

    @Transactional
		public String updateMyWorkout(Long myWorkoutId, MyWorkoutUpdateRequest request) {
    MyWorkout myWorkout = this.findMyWorkoutById(myWorkoutId);
    List<MyWorkout> myWorkouts = this.findAllMyWorkoutWithRoutineId(myWorkout.getRoutine().getId());

    int oldIndex = myWorkout.getMyFitIndex();
    int newIndex = request.getMyWorkoutIndex();

    // 인덱스가 변할 때마다 전체 인덱스 범위 내에서 항목을 밀거나 당기도록 조정
    if (oldIndex > newIndex) {
        // 4에서 1로 이동 시, 원래 1, 2, 3은 2, 3, 4로 밀림
        for (int i = newIndex - 1; i < oldIndex - 1; i++) {
            myWorkouts.get(i).upMyFitIndex();  // 인덱스를 올림
        }
    } else if (oldIndex < newIndex) {
        // 1에서 4로 이동 시, 원래 2, 3, 4는 1, 2, 3으로 당겨짐
        for (int i = oldIndex; i < newIndex; i++) {
            myWorkouts.get(i).downMyFitIndex();  // 인덱스를 내림
        }
    }
    
    // 선택된 항목의 인덱스를 업데이트
    myWorkout.update(request);

    // 상태 저장 후 완료 메시지 반환
    return "[myFitId:" + myWorkout.getId() + "] 수정 완료";
}


    @Transactional
    public String updateMySupplement(Long mySupplementId, MySupplementUpdateRequest request) {
        MySupplement mySupplement = this.findMySupplementById(mySupplementId);
        List<MySupplement> mySupplements = this.findAllMySupplementWithRoutineId(mySupplement.getRoutine().getId());

        int oldIndex = mySupplement.getMyFitIndex();
        int newIndex = request.getMySupplementIndex();
        if(oldIndex > newIndex) {
            //만약 4에서 1로 이동했다면.. 원래 1, 2, 3을 2,3,4로 바꿔준다.
            for (int i = 0; i < (oldIndex-1); i++) {
                mySupplements.get(i).upMyFitIndex();
            }

        } else if(oldIndex < newIndex) {
            //만약 1에서 4로 이동했다면.. 원래 2, 3, 4를 1, 2, 3으로 바꿔준다.
            for (int i = oldIndex; i < newIndex; i++) {
                mySupplements.get(i).downMyFitIndex();
            }
        }

        mySupplement.update(request);
        return "[myFitId:" + mySupplementId +"] 수정 완료";
    }

    @Transactional
    public String deleteMyWorkout(Long myWorkoutId) {
        MyWorkout myWorkout = this.findMyWorkoutById(myWorkoutId);
        List<MyWorkout> myWorkouts = this.findAllMyWorkoutWithRoutineId(myWorkout.getRoutine().getId());

        int myWorkoutIdx = myWorkouts.indexOf(myWorkout);
        if(myWorkoutIdx < (myWorkouts.size()-1)) {
            for (int i = myWorkoutIdx; i < myWorkouts.size(); i++) {
                myWorkouts.get(i).downMyFitIndex();
            }
        }

        myFitRepository.remove(myWorkout);
        return "[myFitId:" + myWorkout.getId() +"] 삭제 완료";
    }

    @Transactional
    public String deleteMySupplement(Long mySupplementId) {
        MySupplement mySupplement = this.findMySupplementById(mySupplementId);
        List<MySupplement> mySupplements = this.findAllMySupplementWithRoutineId(mySupplement.getRoutine().getId());

        int mySupplementIdx = mySupplements.indexOf(mySupplement);
        if(mySupplementIdx < (mySupplements.size()-1)) {
            for (int i = mySupplementIdx; i < mySupplements.size(); i++) {
                mySupplements.get(i).downMyFitIndex();
            }
        }

        myFitRepository.remove(mySupplement);
        return "[myFitId:" + mySupplement.getId() +"] 삭제 완료";
    }

    public List<Workout> searchWorkoutWithRoutineId(String searchKeyword, Long routineId) {
        List<Workout> workouts = workoutRepository.searchAll(-1, new WorkoutSearchCond(searchKeyword, null));
        List<MyWorkout> myWorkouts = this.findAllMyWorkoutWithRoutineId(routineId);

        //routine에 속해있는 운동은 검색 결과에서 제외
        List<Workout> filteredWorkouts = new ArrayList<>();
        for (Workout workout : workouts) {
            boolean canAdd = true;

            for (MyWorkout myWorkout : myWorkouts) {
                if(myWorkout.getWorkout().getId().equals(workout.getId())) {
                    canAdd = false;
                    break;
                }
            }

            if(canAdd) filteredWorkouts.add(workout);
        }

        return filteredWorkouts;
    }

    public List<Supplement> searchSupplementWithRoutineId(String searchKeyword, Long routineId) {
        List<Supplement> supplements = supplementRepository.searchSupplement(-1L, null, searchKeyword);
        List<MySupplement> mySupplements = this.findAllMySupplementWithRoutineId(routineId);

        List<Supplement> filteredSupplements = new ArrayList<>();
        for (Supplement supplement : supplements) {
            boolean canAdd = true;

            for (MySupplement mySupplement : mySupplements) {
                if(mySupplement.getSupplement().getId().equals(supplement.getId())) {
                    canAdd = false;
                    break;
                }
            }

            if(canAdd) filteredSupplements.add(supplement);
        }

        //filteredSupplemnts 중복 제거
        List<Supplement> result = new ArrayList<>();
        result.add(filteredSupplements.get(0));
        for (int i = 1; i < filteredSupplements.size(); i++) {
            if(!filteredSupplements.get(i).getKoreanName().equals(filteredSupplements.get(i-1).getKoreanName())) {
                result.add(filteredSupplements.get(i));
            }
        }

        return result;
    }
}
