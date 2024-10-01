package FitMate.FitMateBackend.machine.dto;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.machine.entity.Machine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMachineResponse {
    private String englishName;
    private String koreanName;
		private String imgPath;

    public UserMachineResponse(Machine machine) {
        this.englishName = machine.getEnglishName();
        this.koreanName = machine.getKoreanName();
				this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_MACHINE + "/" + machine.getImgFileName();
    }
}
