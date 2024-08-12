package FitMate.FitMateBackend.chanhaleWorking.form.supplement;

import FitMate.FitMateBackend.supplement.entity.SupplementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class SupplementForm {
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


    public String validateFields() {
        log.info("img={}, [{}]", image.getOriginalFilename(), image.getSize());
        if (price < 0) {
            return "잘못된 가격";
        }
        if (servings < 0) {
            return "잘못된 serving 정보";
        }
//        if (proteinPerServing < 0) {
//            return "잘못된 protein per serving 정보";
//        }
//        if (fatPerServing < 0) {
//            return "잘못된 fat per serving 정보";
//        }
//        if (carbohydratePerServing < 0) {
//            return "잘못된 carbohydrate per serving 정보";
//        }
        return "ok";
    }
}
