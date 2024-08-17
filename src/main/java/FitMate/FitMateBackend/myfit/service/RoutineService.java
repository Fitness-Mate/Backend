package FitMate.FitMateBackend.myfit.service;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.myfit.dto.routine.RoutineSetData;
import FitMate.FitMateBackend.myfit.dto.routine.SupplementRoutineUpdateRequest;
import FitMate.FitMateBackend.myfit.entity.Routine;
import FitMate.FitMateBackend.myfit.entity.SupplementRoutine;
import FitMate.FitMateBackend.myfit.entity.WorkoutRoutine;
import FitMate.FitMateBackend.myfit.repository.RoutineRepository;
import FitMate.FitMateBackend.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public Long saveWorkoutRoutine(User user, String routineName, int routineIndex) {
        Routine routine = new WorkoutRoutine(user, routineName, routineIndex);
        Long result = routineRepository.save(routine);
        user.addRoutine(routine);
        return result;
    }

    @Transactional
    public Long saveSupplementRoutine(User user) {
        Routine routine = new SupplementRoutine(user, "기본루틴", 0);
        Long result = routineRepository.save(routine);
        user.addRoutine(routine);
        return result;
    }

    @Transactional
    public void updateRoutine(RoutineSetData routine) {
        Routine findRoutine = findRoutineById(routine.getRoutineId());
        findRoutine.update(routine.getRoutineName(), routine.getRoutineIndex());
    }

    @Transactional
    public void updateSupplementRoutineName(Long userId, SupplementRoutineUpdateRequest request) {
        Routine routine = findSupplementRoutineByUserId(userId);
        routine.update(request.getRoutineName(), 0);
    }

    public Routine findRoutineById(Long routineId) {
        return routineRepository.findById(routineId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ROUTINE_NOT_FOUND_EXCEPTION));
    }

    public Routine findSupplementRoutineByUserId(Long userId) {
        return routineRepository.findSupplementRoutineByUserId(userId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ROUTINE_NOT_FOUND_EXCEPTION));
    }

    public List<WorkoutRoutine> findAllWorkoutRoutineWithIndex(Long userId) {
        return routineRepository.findAllWorkoutRoutineWithIndex(userId);
    }
    @Transactional
    public void removeRoutine(Routine routine) {
        routineRepository.remove(routine);
    }

    @Transactional
    public void setWorkoutRoutines(User user, List<RoutineSetData> routines) {
        if(routines.size() > ServiceConst.ROUTINE_MAX_SIZE)
            throw new CustomException(CustomErrorCode.ROUTINE_SIZE_OVER_EXCEPTION);

        List<Long> routineIds = new ArrayList<>();
        for (RoutineSetData routine : routines) {
            if(routine.getRoutineId() == -1) {
                //create routine
                Long savedRoutineId = this.saveWorkoutRoutine(user, routine.getRoutineName(), routine.getRoutineIndex());
                routineIds.add(savedRoutineId);
            } else {
                //update routine
                routineIds.add(routine.getRoutineId());
                this.updateRoutine(routine);
            }
        }

        //delete routine
        List<WorkoutRoutine> oldRoutines = this.findAllWorkoutRoutineWithIndex(user.getId());
        for (WorkoutRoutine routine : oldRoutines) {
            if(!routineIds.contains(routine.getId())) {
                this.removeRoutine(routine);
            }
        }
    }
}
