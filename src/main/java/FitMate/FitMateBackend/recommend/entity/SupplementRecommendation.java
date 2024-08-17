package FitMate.FitMateBackend.recommend.entity;

import FitMate.FitMateBackend.user.entity.BodyData;
import FitMate.FitMateBackend.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@DiscriminatorValue("Supplement")
public class SupplementRecommendation extends Recommendation {

    private Long monthlyBudget; // 예산
    private String purposes = ""; // purposes 들을 and 로 묶은 것

    @OneToMany(cascade = CascadeType.ALL)
    List<RecommendedSupplement> recommendedSupplements = new ArrayList<>();



    public void addRecommendSupplements(RecommendedSupplement recommendedSupplement) {
        recommendedSupplements.add(recommendedSupplement);
    }

    public static SupplementRecommendation createSupplementRecommendation(BodyData bodyData, User user, List<Purpose> purposes, Long monthlyBudget) {
        SupplementRecommendation supplementRecommendation = new SupplementRecommendation();
        supplementRecommendation.setBodyData(bodyData);
        supplementRecommendation.setUser(user);
        Long idx = 0L;
        log.info(purposes.get(0).name());
        for (Purpose purpose : purposes) {
            idx++;
            supplementRecommendation.purposes.concat(purpose.name());
            if (idx < purposes.size()) {
                supplementRecommendation.purposes.concat(" and ");
            }
        }
        supplementRecommendation.monthlyBudget = monthlyBudget;
        supplementRecommendation.setRecommendationType("Supplement");
        StringBuilder sb = new StringBuilder(" For a");
        sb.append(user.getSex() == "남성" ? " man" : " woman")
                .append(" who is")
                .append(bodyData.describe())
                .append(user.getSex() == "남성" ? " His" : " Her")
                .append(" purpose is");
        for (Purpose purpose : purposes) {
            idx++;
            sb.append(" ").append(EnglishPurpose.values()[purpose.ordinal()].name());
            if (idx < purposes.size()) {
                sb.append(" and");
            }
        }
        sb.append(" choose up to 3 supplements in this list and give me the {name, Reasons} sets. Try not to compare with other supplement in Reasons");
        sb.append(". your budget is ").append(monthlyBudget.toString()).append("Won.");
        supplementRecommendation.setQueryText(sb.toString());
//        String qString = "suggest up to 3 supplements in this list. ";
//        qString = qString.concat(user.getSex() == "남성" ? "man" : "woman").concat(" who is ");
//        qString = qString.concat(bodyData.describe());
//        log.info(bodyData.describe());
//        qString = qString.concat(user.getSex() == "남성" ? " His" : " Her")
//                .concat(" purpose is ");
//        idx = 0L;
//        for (Purpose purpose : purposes) {
//            idx++;
//            qString = qString.concat(EnglishPurpose.values()[purpose.ordinal()].name());
//            if (idx < purposes.size()) {
//                qString = qString.concat(" and ");
//            }
//        }
//        qString = qString.concat(". your budget is ").concat(monthlyBudget.toString()).concat("Won.");
//        supplementRecommendation.setQueryText(qString);
        return supplementRecommendation;
    }
}
