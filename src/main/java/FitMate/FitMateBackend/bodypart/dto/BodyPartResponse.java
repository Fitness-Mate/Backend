package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;

@Data
public class BodyPartResponse {
    private Long id;
    private String englishName;
    private String koreanName;
    private String createdAt;

    public BodyPartResponse(BodyPart bodyPart) {
        this.id = bodyPart.getId();
        this.englishName = bodyPart.getEnglishName();
        this.koreanName = bodyPart.getKoreanName();
        this.createdAt = bodyPart.getCreatedAt().toString();
    }
}
