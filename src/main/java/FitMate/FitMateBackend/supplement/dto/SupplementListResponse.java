package FitMate.FitMateBackend.supplement.dto;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.supplement.entity.Gainer;
import FitMate.FitMateBackend.supplement.entity.Protein;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.supplement.entity.SupplementType;
import FitMate.FitMateBackend.util.S3Util;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplementListResponse {

    private Long id;
    private String koreanCompanyName;
    private String englishCompanyName;
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String supplementType;
    private String flavor;
    private String imageURL;
    private String source;
    private String description;
    private String createdAt;

    public SupplementListResponse(Supplement supplement){
        this.id = supplement.getId();
        this.koreanName = getSupplementName(supplement.getKoreanName());
        this.koreanCompanyName = getCompanyName(supplement.getKoreanName());
        this.englishName = getSupplementName(supplement.getEnglishName());
        this.englishCompanyName = getCompanyName(supplement.getEnglishName());
        this.price = supplement.getPrice();
        this.servings = supplement.getServings();
        this.imageURL = S3Util.getAccessURL(ServiceConst.S3_DIR_SUPPLEMENT, supplement.getImageName());
        this.flavor = supplement.getFlavor();
        this.description = supplement.getDescription();
        if (supplement.getType() == SupplementType.Gainer) {
            this.supplementType = "Gainer";
            this.source = ((Gainer) supplement).getSource();
        }
        if (supplement.getType() == SupplementType.AminoAcid) {
            this.supplementType = "AminoAcid";
        }
        if (supplement.getType() == SupplementType.Protein) {
            this.supplementType = "Protein";
            this.source = ((Protein) supplement).getSource();
        }
        if (supplement.getType() == SupplementType.Other) {
            this.supplementType = "Other";
        }
        this.createdAt = supplement.getCreatedAt().toString();
    }

    /**
     * 2024.03.02 (디자인파트 요구사항)
     * 기존 (SYNTHA-6) Protein Isolate와 같은 보조제 이름을 회사명, 제품명을 분리해서 제공하기 위한 메서드
     */
    private String getSupplementName(String name) {
        return name.substring(name.indexOf(")")+2);
    }
    private String getCompanyName(String name) {
        return name.substring(1, name.indexOf(")"));
    }
}
