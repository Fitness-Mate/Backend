package FitMate.FitMateBackend.workout.dto;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.util.S3Util;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.recommend.entity.RecommendedWorkout;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RecommendData {
    private Long workoutId;
    private String koreanName;
    private String englishName;
    private List<String> machineKoreanName = new ArrayList<>();
    private List<String> bodyPartKoreanName = new ArrayList<>();
    private String description;
    private String imgPath;
    private String weight;
    private String repeat;
    private String set;

    public RecommendData(RecommendedWorkout recommend) {
        this.workoutId = recommend.getWorkout().getId();
        this.koreanName = recommend.getWorkout().getKoreanName();
        this.englishName = recommend.getWorkout().getEnglishName();
        this.description = recommend.getWorkout().getDescription();
        this.imgPath = S3Util.getAccessURL(ServiceConst.S3_DIR_WORKOUT, recommend.getWorkout().getImgFileName());
        this.weight = recommend.getWeight();
        this.repeat = recommend.getRepeats();
        this.set = recommend.getSets();

        for (BodyPart bodyPart : recommend.getWorkout().getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
        for (Machine machine : recommend.getWorkout().getMachines()) {
            this.machineKoreanName.add(machine.getKoreanName());
        }
    }
}
