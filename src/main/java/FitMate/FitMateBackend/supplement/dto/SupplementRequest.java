package FitMate.FitMateBackend.supplement.dto;

import FitMate.FitMateBackend.supplement.entity.SupplementType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SupplementRequest {

    @NotNull(message = "englishName must not be null")
    private String englishName;

    @NotNull(message = "koreanName must not be null")
    private String koreanName;

    @NotNull(message = "price must not be null")
    private Integer price;

    @NotNull(message = "servings must not be null")
    private Float servings;

    @NotNull(message = "description must not be null")
    private String description;

    @NotNull(message = "marketURL must not be null")
    private String marketURL;

    @NotNull(message = "supplementType must not be null")
    private SupplementType supplementType;

    @NotNull(message = "flavor must not be null")
    private String flavor;

    @NotNull(message = "image must not be null")
    private MultipartFile image;

    @NotNull(message = "isCaptain must not be null")
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
