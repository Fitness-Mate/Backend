package FitMate.FitMateBackend.machine.dto;

import FitMate.FitMateBackend.machine.entity.Machine;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MachineResponse {
    private Long id;
    private String koreanName;
    private String englishName;
    private String createdAt;

    public MachineResponse(Machine machine) {
        this.id = machine.getId();
        this.koreanName = machine.getKoreanName();
        this.englishName = machine.getEnglishName();
        this.createdAt = machine.getCreatedAt().toString();
    }
}