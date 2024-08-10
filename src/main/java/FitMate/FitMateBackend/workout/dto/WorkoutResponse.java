package FitMate.FitMateBackend.workout.dto;

import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class WorkoutResponse {
    private Long id;
    private String englishName;
    private String koreanName;
    private String imgPath;
    private String videoLink;
    private String description;
    private final List<String> bodyPartKoreanName = new ArrayList<>();
    private final List<String> machineKoreanName = new ArrayList<>();

    public WorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.englishName = workout.getEnglishName();
        this.koreanName = workout.getKoreanName();
        this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_WORKOUT + "/" + workout.getImgFileName();
        this.videoLink = workout.getVideoLink();
        this.description = workout.getDescription();
        for (BodyPart bodyPart : workout.getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
        for(Machine machine : workout.getMachines()) {
            this.machineKoreanName.add(machine.getKoreanName());
        }
    }
}
