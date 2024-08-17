package FitMate.FitMateBackend.myfit.dto.routine;

import FitMate.FitMateBackend.myfit.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineReadAllResponse {

    private Long routineId;
    private int routineIndex;
    private String routineName;

    //routine에 속해있는 myfit에 관련된 내용도 넣어야함.
    public RoutineReadAllResponse(Routine routine) {
        this.routineId = routine.getId();
        this.routineIndex = routine.getRoutineIndex();
        this.routineName = routine.getRoutineName();
    }
}
