package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.util.S3Util;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BodyPartDto {
    private Long bodyPartId;
    private String englishName;
    private String koreanName;
		private String imgPath;

    public BodyPartDto(BodyPart bodyPart) {
        this.bodyPartId = bodyPart.getId();
        this.englishName = bodyPart.getEnglishName();
        this.koreanName = bodyPart.getKoreanName();
				this.imgPath = S3Util.getAccessURL(ServiceConst.S3_DIR_BODYPART, bodyPart.getImgFileName());
    }
}