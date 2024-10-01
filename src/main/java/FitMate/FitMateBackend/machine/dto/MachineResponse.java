package FitMate.FitMateBackend.machine.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.machine.entity.Machine;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MachineResponse {
    private Long id;
    private String koreanName;
    private String englishName;
		private String imgPath;
    private final List<String> bodyPartKoreanName = new ArrayList<>();
    private String createdAt;

    public MachineResponse(Machine machine) {
        this.id = machine.getId();
        this.koreanName = machine.getKoreanName();
        this.englishName = machine.getEnglishName();
				this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_MACHINE + "/" + machine.getImgFileName();
        for (BodyPart bodyPart : machine.getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
        this.createdAt = machine.getCreatedAt().toString();
    }
}