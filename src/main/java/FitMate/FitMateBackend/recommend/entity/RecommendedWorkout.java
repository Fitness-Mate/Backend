package FitMate.FitMateBackend.recommend.entity;

import FitMate.FitMateBackend.workout.entity.Workout;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RecommendedWorkout {

    @Id @GeneratedValue
    @Column(name = "recommended_workout_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id")
    private WorkoutRecommendation workoutRecommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private String weight;
    private String repeats;
    private String sets;
		private String caution;  // 각 운동에 대한 주의사항을 저장

    public void update(WorkoutRecommendation workoutRecommendation,
                       Workout workout, String weight,
                       String repeats, String sets, String caution) {
        this.workoutRecommendation = workoutRecommendation;
        this.workout = workout;
        this.weight = weight;
        this.repeats = repeats;
        this.sets = sets;
				this.caution = caution;
    }
}
