package FitMate.FitMateBackend.supplement.entity;

import FitMate.FitMateBackend.common.BaseEntity;
import FitMate.FitMateBackend.supplement.dto.SupplementRequest;
import FitMate.FitMateBackend.supplement.dto.SupplementRequestOld;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supplements")
@Getter
@DiscriminatorColumn(name = "supplement_type")
@NoArgsConstructor
public abstract class Supplement extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "supplement_id")
    private Long id;

    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String description;
    private String marketURL;
    private String flavor;
    private SupplementType type;
    private Boolean isCaptain;

    @Setter
    private String imageName;

    public Supplement(SupplementRequest supplementRequest) {
        this.englishName = supplementRequest.getEnglishName();
        this.koreanName = supplementRequest.getKoreanName();
        this.price = supplementRequest.getPrice();
        this.servings = supplementRequest.getServings();
        this.description = supplementRequest.getDescription();
        this.marketURL = supplementRequest.getMarketURL();
        this.flavor = supplementRequest.getFlavor();
        this.type = supplementRequest.getSupplementType();
        this.isCaptain = supplementRequest.getIsCaptain();
    }

    public void updateFields(SupplementRequestOld supplementRequestOld) {
        this.englishName = supplementRequestOld.getEnglishName();
        this.koreanName = supplementRequestOld.getKoreanName();
        this.price = supplementRequestOld.getPrice();
        this.servings = supplementRequestOld.getServings();
        this.description = supplementRequestOld.getDescription();
        this.marketURL = supplementRequestOld.getMarketURL();
        this.flavor = supplementRequestOld.getFlavor();
        this.type = supplementRequestOld.getSupplementType();
        this.isCaptain = supplementRequestOld.getIsCaptain();
    }

    public abstract String createIntroduction();
}
