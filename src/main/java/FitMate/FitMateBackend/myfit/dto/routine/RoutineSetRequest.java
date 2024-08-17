package FitMate.FitMateBackend.myfit.dto.routine;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSetRequest {
    List<RoutineSetData> routines = new ArrayList<>();
}