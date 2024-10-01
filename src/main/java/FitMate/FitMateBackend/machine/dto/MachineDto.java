package FitMate.FitMateBackend.machine.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.util.S3Util;
import FitMate.FitMateBackend.machine.entity.Machine;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MachineDto {
    private Long id;
    private String englishName;
    private String koreanName;
    private List<String> bodyPartKoreanName = new ArrayList<>();
		private String imgPath;

    public MachineDto(Machine machine) {
        this.id = machine.getId();
        this.englishName = machine.getEnglishName();
        this.koreanName = machine.getKoreanName();
        for (BodyPart bodyPart : machine.getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
				this.imgPath = S3Util.getAccessURL(ServiceConst.S3_DIR_MACHINE, machine.getImgFileName());
    }
}