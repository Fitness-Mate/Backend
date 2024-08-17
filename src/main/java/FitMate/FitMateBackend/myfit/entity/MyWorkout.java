package FitMate.FitMateBackend.myfit.entity;

import FitMate.FitMateBackend.myfit.dto.myWorkout.MyWorkoutCreateRequest;
import FitMate.FitMateBackend.myfit.dto.myWorkout.MyWorkoutUpdateRequest;
import FitMate.FitMateBackend.workout.entity.Workout;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MyWorkout extends MyFit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private String weight;
    private String rep;
    private String setCount;

    public MyWorkout(Routine routine, Workout workout, int myFitIndex) {
        super(routine, myFitIndex);
        this.workout = workout;
    }
    public MyWorkout(Routine routine, Workout workout, MyWorkoutCreateRequest request, int myFitIndex) {
        super(routine, myFitIndex);
        this.workout = workout;
        this.weight = request.getWeight();
        this.rep = request.getRep();
        this.setCount = request.getSetCount();
    }

    public void update(MyWorkoutUpdateRequest request) {
        this.setMyFitIndex(request.getMyWorkoutIndex());
        this.weight = request.getWeight();
        this.rep = request.getRep();
        this.setCount = request.getSetCount();
    }
}