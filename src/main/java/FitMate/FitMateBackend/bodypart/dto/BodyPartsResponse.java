package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BodyPartsResponse {
    private List<BodyPartName> bodyPartKoreanName = new ArrayList<>();

    public BodyPartsResponse(List<BodyPart> bodyParts) {
        for (BodyPart bodyPart : bodyParts) {
            bodyPartKoreanName.add(new BodyPartName(bodyPart.getEnglishName(), bodyPart.getKoreanName()));
        }
    }
}

@Data
@AllArgsConstructor
class BodyPartName {
    private String englishName;
    private String koreanName;
}
