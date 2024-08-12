package FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.domain.myfit.MySupplement;
import FitMate.FitMateBackend.supplement.entity.AminoAcid;
import FitMate.FitMateBackend.supplement.entity.Gainer;
import FitMate.FitMateBackend.supplement.entity.Other;
import FitMate.FitMateBackend.supplement.entity.Protein;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.common.util.S3Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementReadData {
    private Long mySupplementId;
    private int mySupplementIndex;

    private Long supplementId;
    private String supplementName;
    private String imageURL;
    private String description;
    private String supplementType;
    private String flavor;
    private int price;

    // protein, Gainer
    private Float proteinPerServing;
    private Float fatPerServing;
    private Float carbohydratePerServing;
    private String source;

    // AminoAcid
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

    //Other
    private String contains;

    public MySupplementReadData(MySupplement mySupplement) {
        this.mySupplementId = mySupplement.getId();
        this.mySupplementIndex = mySupplement.getMyFitIndex();

        Supplement supplement = mySupplement.getSupplement();
        this.supplementId = supplement.getId();
        this.supplementName = supplement.getKoreanName();
        this.imageURL = S3Util.getAccessURL(ServiceConst.S3_DIR_SUPPLEMENT, supplement.getImageName());
        this.description = supplement.getDescription();
        this.supplementType = supplement.getType().toString();
        this.flavor = supplement.getFlavor();
        this.price = supplement.getPrice();

        if (supplement instanceof Gainer) {
            this.supplementType = "Gainer";
            Gainer sup = (Gainer) supplement;
            this.proteinPerServing = sup.getProteinPerServing();
            this.fatPerServing = sup.getFatPerServing();
            this.carbohydratePerServing = sup.getCarbohydratePerServing();
            this.source = sup.getSource();
        } else if(supplement instanceof Protein) {
            this.supplementType = "Protein";
            Protein sup = (Protein) supplement;
            this.proteinPerServing = sup.getProteinPerServing();
            this.fatPerServing = sup.getFatPerServing();
            this.carbohydratePerServing = sup.getCarbohydratePerServing();
            this.source = sup.getSource();
        } else if (supplement instanceof AminoAcid) {
            this.supplementType = "AminoAcid";
            AminoAcid sup = (AminoAcid) supplement;
            this.leucine = sup.getLeucine();
            this.isoLeucine = sup.getIsoLeucine();
            this.valine = sup.getValine();
            this.L_Carnitine = sup.getL_Carnitine();
            this.L_Glutamine = sup.getL_Glutamine();
            this.L_Alanine = sup.getL_Alanine();
            this.L_Lysine = sup.getL_Lysine();
            this.methionine = sup.getMethionine();
            this.phenylalanine = sup.getPhenylalanine();
            this.threonine = sup.getThreonine();
            this.histidine = sup.getHistidine();
            this.tryptophan = sup.getTryptophan();
        } else if (supplement instanceof Other) {
            this.supplementType = "Other";
            Other sup = (Other) supplement;
            this.contains = sup.getContains();
        }
    }
}
