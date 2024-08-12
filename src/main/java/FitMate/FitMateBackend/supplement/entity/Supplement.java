package FitMate.FitMateBackend.supplement.entity;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import FitMate.FitMateBackend.common.BaseEntity;
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

    public Supplement(SupplementForm supplementForm) {
        this.englishName = supplementForm.getEnglishName();
        this.koreanName = supplementForm.getKoreanName();
        this.price = supplementForm.getPrice();
        this.servings = supplementForm.getServings();
        this.description = supplementForm.getDescription();
        this.marketURL = supplementForm.getMarketURL();
        this.flavor = supplementForm.getFlavor();
        this.type = supplementForm.getSupplementType();
        this.isCaptain = supplementForm.getIsCaptain();
    }

    public void updateFields(SupplementForm supplementForm) {
        this.englishName = supplementForm.getEnglishName();
        this.koreanName = supplementForm.getKoreanName();
        this.price = supplementForm.getPrice();
        this.servings = supplementForm.getServings();
        this.description = supplementForm.getDescription();
        this.marketURL = supplementForm.getMarketURL();
        this.flavor = supplementForm.getFlavor();
        this.type = supplementForm.getSupplementType();
        this.isCaptain = supplementForm.getIsCaptain();
    }

    public abstract String createIntroduction();
}
