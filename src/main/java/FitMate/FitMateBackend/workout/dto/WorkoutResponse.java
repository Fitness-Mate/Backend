package FitMate.FitMateBackend.workout.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkoutResponse {
    private Long id;
    private String englishName;
    private String koreanName;
    private String imgPath;
    private String videoLink;
    private String description;
		private String atcetera;
    private final List<String> bodyPartKoreanName = new ArrayList<>();
    private final List<String> machineKoreanName = new ArrayList<>();
    private String createdAt;

    public WorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.englishName = workout.getEnglishName();
        this.koreanName = workout.getKoreanName();
        this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_WORKOUT + "/" + workout.getImgFileName();
        this.videoLink = workout.getVideoLink();
        this.atcetera = workout.getAtcetera();
        for (BodyPart bodyPart : workout.getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
        for(Machine machine : workout.getMachines()) {
            this.machineKoreanName.add(machine.getKoreanName());
        }
        this.createdAt = workout.getCreatedAt().toString();
    }
}
