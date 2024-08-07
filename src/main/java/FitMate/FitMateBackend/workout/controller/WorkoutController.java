package FitMate.FitMateBackend.workout.controller;

import FitMate.FitMateBackend.workout.dto.WorkoutRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.dto.WorkoutResponse;
import FitMate.FitMateBackend.workout.service.WorkoutServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutServiceImpl workoutService;

    @PostMapping("/search/list/{page}") //운동 페이지 검색
    public ResponseEntity<List<WorkoutResponse>> search(
        @PathVariable("page") int page,
        @RequestBody WorkoutSearchCond cond
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.searchAll(page, cond));
    }

    @GetMapping("/{workoutId}") //운동 단건조회
    public ResponseEntity<WorkoutResponse> findOne(
        @PathVariable("workoutId") Long workoutId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.findById(workoutId));
    }

    @PutMapping("/{workoutId}") //운동 수정
    public ResponseEntity<WorkoutResponse> updateWorkout(
        @PathVariable("workoutId") Long workoutId,
        @ModelAttribute WorkoutRequest form
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.update(form, workoutId));
    }

    @DeleteMapping("/{workoutId}") //운동 삭제
    public ResponseEntity<WorkoutResponse> deleteWorkout(
        @PathVariable("workoutId") Long workoutId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.remove(workoutId));
    }
}
