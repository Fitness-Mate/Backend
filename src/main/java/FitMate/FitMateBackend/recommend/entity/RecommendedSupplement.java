package FitMate.FitMateBackend.recommend.entity;

import FitMate.FitMateBackend.supplement.entity.Supplement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "recommended_supplements")
@NoArgsConstructor
public class RecommendedSupplement {
    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    SupplementRecommendation supplementRecommendation;


    @ManyToOne
    private Supplement supplement;
    @Column(length = 2000)
    private String koreanRecommendationString;

    public static RecommendedSupplement createRecommendedSupplement(Supplement supplement, String koreanRecommendationString) {
        RecommendedSupplement recommendedSupplement = new RecommendedSupplement();
        recommendedSupplement.supplement = supplement;
        recommendedSupplement.koreanRecommendationString = koreanRecommendationString;
        return recommendedSupplement;
    }
}
