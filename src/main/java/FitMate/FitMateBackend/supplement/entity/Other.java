package FitMate.FitMateBackend.supplement.entity;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("OtherSupplement")
@NoArgsConstructor
public class Other extends Supplement {
    private String contains;
    public Other(SupplementForm supplementForm) {
        super(supplementForm);
        this.contains = supplementForm.getContains();
    }
    @Override
    public String createIntroduction() {
        return "{" +
                "Name: \""+ ServiceConst.RECOMMEND_PREFIX +this.getId()+ ServiceConst.RECOMMEND_SUFFIX+"\", "+
                "type: "+this.getType()+", "+
                "price: "+this.getPrice()+"Won, "+
                "servings: "+this.getServings()+"serving, "+
                "contains: "+this.contains+"}";
    }
    public void updateFields(SupplementForm supplementForm) {
        super.updateFields(supplementForm);
        this.contains = supplementForm.getContains();
    }
}
