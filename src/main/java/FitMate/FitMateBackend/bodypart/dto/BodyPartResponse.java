package FitMate.FitMateBackend.bodypart.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import lombok.Data;

@Data
public class BodyPartResponse {
    private Long id;
    private String englishName;
    private String koreanName;
    private String createdAt;
		private String imgPath;

    public BodyPartResponse(BodyPart bodyPart) {
        this.id = bodyPart.getId();
        this.englishName = bodyPart.getEnglishName();
        this.koreanName = bodyPart.getKoreanName();
				this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_BODYPART + "/" + bodyPart.getImgFileName();
        this.createdAt = bodyPart.getCreatedAt().toString();
    }
}
