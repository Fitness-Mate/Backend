package FitMate.FitMateBackend.myfit.entity;

import FitMate.FitMateBackend.user.entity.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WorkoutRoutine extends Routine {

    public WorkoutRoutine(User user, String routineName, int routineIndex) {
        super(user, routineName, routineIndex);
    }
}