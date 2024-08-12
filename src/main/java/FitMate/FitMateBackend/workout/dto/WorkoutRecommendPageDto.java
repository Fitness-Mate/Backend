package FitMate.FitMateBackend.workout.dto;

import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WorkoutRecommendPageDto {
    private Long recommendId;
    private LocalDate date;
    private List<String> requestedBodyParts = new ArrayList<>();
    private List<RecommendData> workouts = new ArrayList<>();

    public WorkoutRecommendPageDto(WorkoutRecommendation workoutRecommendation) {
        this.recommendId = workoutRecommendation.getId();
        this.date = workoutRecommendation.getDate();
        String[] bodyPartsKoreanName = workoutRecommendation.getRequestedBodyParts().split(",");
        for (String koreanName : bodyPartsKoreanName) {
            this.requestedBodyParts.add(koreanName);
        }
        for (RecommendedWorkout recommend : workoutRecommendation.getRws()) {
            workouts.add(new RecommendData(recommend));
        }
    }
}