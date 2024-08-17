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

    public AminoAcid(SupplementRequest supplementRequest) {
        super(supplementRequest);
        this.leucine = supplementRequest.getLeucine();
        this.isoLeucine = supplementRequest.getIsoLeucine();
        this.valine = supplementRequest.getValine();
        this.L_Carnitine = supplementRequest.getL_Carnitine();
        this.L_Glutamine = supplementRequest.getL_Glutamine();
        this.L_Alanine = supplementRequest.getL_Alanine();
        this.L_Lysine = supplementRequest.getL_Lysine();
        this.methionine = supplementRequest.getMethionine();
        this.phenylalanine = supplementRequest.getPhenylalanine();
        this.threonine = supplementRequest.getThreonine();
        this.histidine = supplementRequest.getHistidine();
        this.tryptophan = supplementRequest.getTryptophan();
    }
    public void updateFields(SupplementRequestOld supplementRequestOld) {
        super.updateFields(supplementRequestOld);
        this.leucine = supplementRequestOld.getLeucine();
        this.isoLeucine = supplementRequestOld.getIsoLeucine();
        this.valine = supplementRequestOld.getValine();
        this.L_Carnitine = supplementRequestOld.getL_Carnitine();
        this.L_Glutamine = supplementRequestOld.getL_Glutamine();
        this.L_Alanine = supplementRequestOld.getL_Alanine();
        this.L_Lysine = supplementRequestOld.getL_Lysine();
        this.methionine = supplementRequestOld.getMethionine();
        this.phenylalanine = supplementRequestOld.getPhenylalanine();
        this.threonine = supplementRequestOld.getThreonine();
        this.histidine = supplementRequestOld.getHistidine();
        this.tryptophan = supplementRequestOld.getTryptophan();
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
