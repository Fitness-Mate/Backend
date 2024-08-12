package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.chanhaleWorking.service.ChatGptService;
import FitMate.FitMateBackend.workout.dto.RecommendedWorkoutResponse;
import FitMate.FitMateBackend.workout.dto.WorkoutRecommendPageDto;
import FitMate.FitMateBackend.workout.dto.WorkoutRecommendationRequest;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.cjjsWorking.service.recommendService.RecommendedWorkoutService;
import FitMate.FitMateBackend.cjjsWorking.service.recommendService.WorkoutRecommendationService;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "WorkoutRecommendationController")
public class WorkoutRecommendationController {

    private final WorkoutRecommendationService workoutRecommendationService;
    private final ChatGptService chatGptService;
    private final RecommendedWorkoutService recommendedWorkoutService;

    @PostMapping("recommendation/workout") //운동 추천 요청
    public Long getWorkoutRecommendation(@RequestHeader HttpHeaders header, @RequestBody WorkoutRecommendationRequest request) throws Exception {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        Long recommendationId = workoutRecommendationService.
                createWorkoutRecommendation(userId, request);

        WorkoutRecommendation workoutRecommendation = workoutRecommendationService.findById(recommendationId);
        String question = workoutRecommendation.getQueryText();

        log.info(question);
        chatGptService.sendWorkoutRequest(workoutRecommendation.getId(), question);
        return recommendationId;
    }

    @GetMapping("recommendation/workout/history/list/{page}") //운동 추천 history batch 요청
    public List<WorkoutRecommendPageDto> findRecommendedWorkouts_page(@RequestHeader HttpHeaders header,
                                                                      @PathVariable("page") int page) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        List<WorkoutRecommendation> findWR = workoutRecommendationService.findAllWithWorkoutRecommendation(page, userId);

        return findWR
                .stream().map(WorkoutRecommendPageDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("recommendation/workout/history/{workoutRecommendationId}") //운동 추천 history 단건 요청
    public RecommendedWorkoutResponse findRecommendedWorkout(@PathVariable("workoutRecommendationId") Long workoutRecommendationId) {
        List<RecommendedWorkout> findRecommend = recommendedWorkoutService.findById(workoutRecommendationId);
        return new RecommendedWorkoutResponse(findRecommend);
    }

    @DeleteMapping("recommendation/workout/history/{workoutRecommendationId}") //운동 추천 history 삭제
    public void deleteWorkoutRecommendation(@PathVariable("workoutRecommendationId") Long workoutRecommendationId) {
        workoutRecommendationService.deleteWorkoutRecommendation(workoutRecommendationId);
    }
}