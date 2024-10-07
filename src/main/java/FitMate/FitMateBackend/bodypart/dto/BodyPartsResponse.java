package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BodyPartsResponse extends ArrayList<BodyPartName> {
    private List<BodyPartName> bodyPartKoreanName = new ArrayList<>();

    public BodyPartsResponse(List<BodyPart> bodyParts) {
        for (BodyPart bodyPart : bodyParts) {
            add(new BodyPartName(bodyPart.getId(), bodyPart.getEnglishName(), bodyPart.getKoreanName()));
        }
    }
}

@Data
@AllArgsConstructor
class BodyPartName {
		private Long bodyPartId;
    private String englishName;
    private String koreanName;
}
