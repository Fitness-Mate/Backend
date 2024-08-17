package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.recommend.entity.SupplementRecommendation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class SupplementRecommendationDto {
    private Long supplementRecommendationId;
    private LocalDate date;
    private Long monthlyBudget;
    private String purposes;
    private List<RecommendedSupplementDto> recommendedSupplementList;

    public static SupplementRecommendationDto createSupplementRecommendationDto(SupplementRecommendation supplementRecommendation) {
        SupplementRecommendationDto supplementRecommendationDto = new SupplementRecommendationDto();
        supplementRecommendationDto.supplementRecommendationId = supplementRecommendation.getId();
        supplementRecommendationDto.date = supplementRecommendation.getDate();
        supplementRecommendationDto.monthlyBudget = supplementRecommendation.getMonthlyBudget();
        supplementRecommendationDto.purposes = supplementRecommendation.getPurposes();
        supplementRecommendationDto.recommendedSupplementList = new ArrayList<>();
        return supplementRecommendationDto;
    }

    public void addRecommendedSupplement(RecommendedSupplementDto recommendedSupplementDto) {
        recommendedSupplementList.add(recommendedSupplementDto);
    }
}
