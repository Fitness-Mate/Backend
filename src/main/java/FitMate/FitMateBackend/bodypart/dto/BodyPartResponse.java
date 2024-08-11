package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;

@Data
public class BodyPartResponse {
    private Long id;
    private String englishName;
    private String koreanName;

    public BodyPartResponse(BodyPart bodyPart) {
        this.id = bodyPart.getId();
        this.englishName = bodyPart.getEnglishName();
        this.koreanName = bodyPart.getKoreanName();
    }
}
