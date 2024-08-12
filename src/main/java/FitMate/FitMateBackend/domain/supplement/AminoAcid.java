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
@DiscriminatorValue("AminoAcid")
@NoArgsConstructor
public class AminoAcid extends Supplement {


    private Float leucine;
    private Float isoLeucine;
    private Float valine;
    private Float L_Carnitine;
    private Float L_Glutamine;
    private Float L_Alanine;
    private Float L_Lysine;
    private Float methionine;
    private Float phenylalanine;
    private Float threonine;
    private Float histidine;
    private Float tryptophan;
    public AminoAcid(SupplementForm supplementForm) {
        super(supplementForm);
        this.leucine = supplementForm.getLeucine();
        this.isoLeucine = supplementForm.getIsoLeucine();
        this.valine = supplementForm.getValine();
        this.L_Carnitine = supplementForm.getL_Carnitine();
        this.L_Glutamine = supplementForm.getL_Glutamine();
        this.L_Alanine = supplementForm.getL_Alanine();
        this.L_Lysine = supplementForm.getL_Lysine();
        this.methionine = supplementForm.getMethionine();
        this.phenylalanine = supplementForm.getPhenylalanine();
        this.threonine = supplementForm.getThreonine();
        this.histidine = supplementForm.getHistidine();
        this.tryptophan = supplementForm.getTryptophan();
    }
    public void updateFields(SupplementForm supplementForm) {
        super.updateFields(supplementForm);
        this.leucine = supplementForm.getLeucine();
        this.isoLeucine = supplementForm.getIsoLeucine();
        this.valine = supplementForm.getValine();
        this.L_Carnitine = supplementForm.getL_Carnitine();
        this.L_Glutamine = supplementForm.getL_Glutamine();
        this.L_Alanine = supplementForm.getL_Alanine();
        this.L_Lysine = supplementForm.getL_Lysine();
        this.methionine = supplementForm.getMethionine();
        this.phenylalanine = supplementForm.getPhenylalanine();
        this.threonine = supplementForm.getThreonine();
        this.histidine = supplementForm.getHistidine();
        this.tryptophan = supplementForm.getTryptophan();
    }

    @Override
    public String createIntroduction() {
        StringBuilder sb = new StringBuilder("{" +
                "Name: \"" + ServiceConst.RECOMMEND_PREFIX + this.getId() + ServiceConst.RECOMMEND_SUFFIX + "\", " +
                "type: " + this.getType() + ", " +
                "price: " + this.getPrice() + "Won, "+
                "servings: "+this.getServings()+"serving, ");
        if (leucine != null && leucine != 0f) {
            sb.append("leucine: ").append(this.leucine).append("g, ");
        }
        if (isoLeucine != null && isoLeucine != 0f) {
            sb.append("isoLeucine: ").append(this.isoLeucine).append("g, ");
        }
        if (valine != null && valine != 0f) {
            sb.append("valine: ").append(this.valine).append("g, ");
        }
        if (L_Carnitine != null && L_Carnitine != 0f) {
            sb.append("L-Carnitine: ").append(this.L_Carnitine).append("g, ");
        }
        if (L_Glutamine != null && L_Glutamine != 0f) {
            sb.append("L_Glutamine: ").append(this.L_Glutamine).append("g, ");
        }
        if (L_Alanine != null && L_Alanine != 0f) {
            sb.append("L_Alanine: ").append(this.L_Alanine).append("g, ");
        }
        if (L_Lysine != null && L_Lysine != 0f) {
            sb.append("L_Lysine: ").append(this.L_Lysine).append("g, ");
        }
        if (methionine != null && methionine != 0f) {
            sb.append("methionine: ").append(this.methionine).append("g, ");
        }
        if (phenylalanine != null && phenylalanine != 0f) {
            sb.append("phenylalanine: ").append(this.phenylalanine).append("g, ");
        }
        if (threonine != null && threonine != 0f) {
            sb.append("threonine: ").append(this.threonine).append("g, ");
        }
        if (histidine != null && histidine != 0f) {
            sb.append("histidine: ").append(this.histidine).append("g, ");
        }
        if (tryptophan != null && tryptophan != 0f) {
            sb.append("tryptophan: ").append(this.tryptophan).append("g, ");
        }
        sb.append("}");
        return sb.toString();
    }
}
