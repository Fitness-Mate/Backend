package FitMate.FitMateBackend.cjjsWorking.dto.Machine;

import FitMate.FitMateBackend.domain.Machine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMachineResponse {
    private String englishName;
    private String koreanName;

    public UserMachineResponse(Machine machine) {
        this.englishName = machine.getEnglishName();
        this.koreanName = machine.getKoreanName();
    }
}
