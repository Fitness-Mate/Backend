package FitMate.FitMateBackend.chanhaleWorking.form.recommendation;

import FitMate.FitMateBackend.recommend.entity.Purpose;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplementRecommendationForm {
    private Long monthlyBudget;
    private List<Purpose> purpose;
}
