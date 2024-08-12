package FitMate.FitMateBackend.machine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMachineRequest {
    private List<String> bodyPartKoreanName;
}
