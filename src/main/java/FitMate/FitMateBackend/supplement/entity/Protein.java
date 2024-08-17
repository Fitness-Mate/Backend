package FitMate.FitMateBackend.supplement.entity;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.supplement.dto.SupplementRequest;
import FitMate.FitMateBackend.supplement.dto.SupplementRequestOld;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Protein")
@NoArgsConstructor
public class Protein extends Supplement {
    private Float proteinPerServing;
    private Float fatPerServing;
    private Float carbohydratePerServing;
    // 분리유청단백, isolate, 대두단백
    private String source;

    public Protein(SupplementRequest supplementRequest) {
        super(supplementRequest);
        this.proteinPerServing = supplementRequest.getProteinPerServing();
        this.fatPerServing = supplementRequest.getFatPerServing();
        this.carbohydratePerServing = supplementRequest.getCarbohydratePerServing();
        this.source = supplementRequest.getSource();
    }
    public void updateFields(SupplementRequestOld supplementRequestOld) {
        super.updateFields(supplementRequestOld);
        this.proteinPerServing = supplementRequestOld.getProteinPerServing();
        this.fatPerServing = supplementRequestOld.getFatPerServing();
        this.carbohydratePerServing = supplementRequestOld.getCarbohydratePerServing();
        this.source = supplementRequestOld.getSource();
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
