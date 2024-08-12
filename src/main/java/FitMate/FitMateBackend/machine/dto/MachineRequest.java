package FitMate.FitMateBackend.machine.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MachineRequest {
    private String englishName;
    private String koreanName;
    private List<Long> bodyPartIdList;
    private List<String> bodyPartKoreanName;
}