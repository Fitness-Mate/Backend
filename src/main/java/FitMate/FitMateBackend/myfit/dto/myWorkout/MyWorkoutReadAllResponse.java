package FitMate.FitMateBackend.myfit.dto.myWorkout;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.common.util.S3Util;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.myfit.entity.MyWorkout;
import FitMate.FitMateBackend.workout.entity.Workout;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyWorkoutReadAllResponse {
    private Long myWorkoutId;
    private int myWorkoutIndex;
    private String weight;
    private String rep;
    private String setCount;
		private String caution;

    private Long workoutId;
    private String workoutName;
    private String imgPath;
    private String description;
		private String atcetera;
    private List<String> bodyParts = new ArrayList<>();
    private List<String> machines = new ArrayList<>();

    public MyWorkoutReadAllResponse(MyWorkout myWorkout) {
        this.myWorkoutId = myWorkout.getId();
        this.myWorkoutIndex = myWorkout.getMyFitIndex();
        this.rep = myWorkout.getRep();
        this.setCount = myWorkout.getSetCount();
        this.weight = myWorkout.getWeight();
				this.caution = myWorkout.getCaution();

        Workout workout = myWorkout.getWorkout();
        this.workoutId = workout.getId();
        this.workoutName = workout.getKoreanName();
        this.imgPath = S3Util.getAccessURL(ServiceConst.S3_DIR_WORKOUT, workout.getImgFileName());
        this.description = workout.getDescription();
				this.atcetera = workout.getAtcetera();

        for (BodyPart bodyPart : workout.getBodyParts()) {
            bodyParts.add(bodyPart.getKoreanName());
        }
        for (Machine machine : workout.getMachines()) {
            machines.add(machine.getKoreanName());
        }
    }
}
