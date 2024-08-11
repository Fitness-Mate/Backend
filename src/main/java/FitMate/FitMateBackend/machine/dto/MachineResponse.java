package FitMate.FitMateBackend.machine.dto;

import FitMate.FitMateBackend.domain.Machine;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MachineResponse {
    private Long id;
    private String koreanName;
    private String englishName;

    public MachineResponse(Machine machine) {
        this.id = machine.getId();
        this.englishName = machine.getEnglishName();
        this.koreanName = machine.getKoreanName();
    }
}