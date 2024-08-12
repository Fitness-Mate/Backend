package FitMate.FitMateBackend.domain.supplement;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Gainer")
@NoArgsConstructor
public class Gainer extends Supplement {
    private Float proteinPerServing;
    private Float fatPerServing;
    private Float carbohydratePerServing;
    private String source;

    public Gainer(SupplementForm supplementForm) {
        super(supplementForm);
        this.proteinPerServing = supplementForm.getProteinPerServing();
        this.fatPerServing = supplementForm.getFatPerServing();
        this.carbohydratePerServing = supplementForm.getCarbohydratePerServing();
        this.source = supplementForm.getSource();
    }
    public void updateFields(SupplementForm supplementForm) {
        super.updateFields(supplementForm);
        this.proteinPerServing = supplementForm.getProteinPerServing();
        this.fatPerServing = supplementForm.getFatPerServing();
        this.carbohydratePerServing = supplementForm.getCarbohydratePerServing();
        this.source = supplementForm.getSource();
    }


    @Override
    public String createIntroduction() {
        return "{" +
                "Name: \""+ ServiceConst.RECOMMEND_PREFIX +this.getId()+ ServiceConst.RECOMMEND_SUFFIX+"\", "+
                "type: "+this.getType()+", "+
                "price: "+this.getPrice()+"Won, "+
                "servings: "+this.getServings()+"serving, "+
                "protein: "+this.proteinPerServing+"g, "+
                "fat: "+this.fatPerServing+"g, "+
                "carbohydrate: "+this.carbohydratePerServing+"g}"
                ;
    }
}
