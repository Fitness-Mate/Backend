package FitMate.FitMateBackend.myfit.dto.mySupplement;

import FitMate.FitMateBackend.supplement.entity.Supplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementSearchResponse {
    private Long supplementId;
    private String supplementName;

    public MySupplementSearchResponse(Supplement supplement) {
        this.supplementId = supplement.getId();
        this.supplementName = supplement.getKoreanName();
    }
}
