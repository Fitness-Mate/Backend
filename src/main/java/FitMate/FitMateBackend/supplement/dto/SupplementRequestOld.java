package FitMate.FitMateBackend.supplement.dto;

import FitMate.FitMateBackend.supplement.entity.SupplementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplementRequestOld {
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String description;
    private String marketURL;
    private SupplementType supplementType;
    private String flavor;
    private MultipartFile image;
    private Boolean isCaptain;

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
}
