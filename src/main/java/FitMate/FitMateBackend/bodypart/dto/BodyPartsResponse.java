package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;

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
            // imgPath를 bodyPart.getImgFileName()을 사용해 생성
            String imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_BODYPART + "/" + bodyPart.getImgFileName();
            add(new BodyPartName(bodyPart.getId(), bodyPart.getEnglishName(), bodyPart.getKoreanName(), imgPath));
        }
    }
}

@Data
@AllArgsConstructor
class BodyPartName {
		private Long bodyPartId;
    private String englishName;
    private String koreanName;
		private String imgPath;
}
