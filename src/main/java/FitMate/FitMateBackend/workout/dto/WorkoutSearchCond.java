package FitMate.FitMateBackend.workout.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSearchCond {
    private String searchKeyword;
    private List<String> bodyPartKoreanName;
}