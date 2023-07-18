package FitMate.FitMateBackend.cjjsWorking.dto.bodyPart;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BodyPartDto {
    private Long id;
    private String englishName;
    private String koreanName;

    public BodyPartDto(BodyPart bodyPart) {
        this.id = bodyPart.getId();
        this.englishName = bodyPart.getEnglishName();
        this.koreanName = bodyPart.getKoreanName();
    }
}