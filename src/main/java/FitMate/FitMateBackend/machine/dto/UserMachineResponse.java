package FitMate.FitMateBackend.machine.dto;

import FitMate.FitMateBackend.machine.entity.Machine;
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
