package FitMate.FitMateBackend.myfit.entity;

import FitMate.FitMateBackend.user.entity.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SupplementRoutine extends Routine {

    public SupplementRoutine(User user, String routineName, int routineIndex) {
        super(user, routineName, routineIndex);
    }
}
