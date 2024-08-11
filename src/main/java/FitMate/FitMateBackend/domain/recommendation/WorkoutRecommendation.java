package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.machine.entity.Machine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Workout")
public class WorkoutRecommendation extends Recommendation {

    @JsonIgnore
    @OneToMany(mappedBy = "workoutRecommendation", cascade = CascadeType.ALL)
    private List<RecommendedWorkout> rws = new ArrayList<>();

    private String requestedBodyParts;
    private String requestedMachines;

    public static WorkoutRecommendation createWorkoutRecommendation
            (User user, List<BodyPart> bodyParts, List<Machine> machines, String workoutList) {
        WorkoutRecommendation workoutRecommendation = new WorkoutRecommendation();
        BodyData bodyData = user.getBodyDataHistory().get(user.getBodyDataHistory().size() - 1);
        workoutRecommendation.setBodyData(bodyData);
        workoutRecommendation.setUser(user);

        //bodyPart 필수선택
        String bodyPartString = "";
        for (int i = 0; i < bodyParts.size(); i++) {
            if(i == (bodyParts.size()-1)) bodyPartString = bodyPartString.concat(bodyParts.get(i).getKoreanName());
            else bodyPartString = bodyPartString.concat(bodyParts.get(i).getKoreanName()).concat(",");
        }
        workoutRecommendation.setRequestedBodyParts(bodyPartString);

        //machine 필수선택
        String machineString = "";
        for (int i = 0; i < machines.size(); i++) {
            if(i == (machines.size())-1) machineString = machineString.concat(machines.get(i).getKoreanName());
            else machineString = machineString.concat(machines.get(i).getKoreanName()).concat(",");
        }
        workoutRecommendation.setRequestedMachines(machineString);

        workoutRecommendation.setRecommendationType("Workout");

        String bodyPartQuery = updateBodyPartQuery(bodyParts);
        String machineQuery = updateMachineQuery(machines);

        String qString = "You have to recommend an exercise considering my gender, height, weight, body composition information," +
                " and the exercise part and exercise equipment I want. I'm ";

        qString = qString.concat(user.getSex().equals("남성") ? "male, " : "female, ").concat(bodyData.describe());
        qString = qString.concat(" The part I want to exercise is my ");
        qString = qString.concat(bodyPartQuery).concat(" and I want to use a ");
        if(machineQuery != null) qString = qString.concat(machineQuery).concat("\n\n");

        qString = qString.concat("When answering, be sure to observe the requirements below.\n" +
                "1. Find a workout from the list below and recommend it.\n");
        qString = qString.concat(workoutList).concat("\n\n");
        qString = qString.concat("2. When answering, do not add any comments," +
                " and answer the ten exercises in the form " +
                "[workout index in list] [weight(kg)] [repeat] [set] in exactly ten lines, one line for each exercise. " +
                "Below is an example for you to answer\n" +
                "[150056][40kg][12][4]\n" +
                "[99955][60kg][10][5]\n" +
                "[99961][40kg][10][5]\n" +
                "[50112][30kg][10][5]\n" +
                "[249962][30kg][10][5]\n" +
                "[249953][30kg][10][5]\n" +
                "[249960][30kg][10][5]\n" +
                "[200005][30kg][10][5]\n" +
                "[99960][30kg][10][5]\n" +
                "[50109][40kg][12][4]\n\n");
        qString = qString.concat("3. When recommending a weight, " +
                "please suggest an exact number (kg) instead of an ambiguous expression such as a medium weight.\n\n");
        qString = qString.concat("4. Only recommend exercises that use the exercise equipment I suggested. " +
                "The recommended exercise and the equipment used must match.");

        workoutRecommendation.setQueryText(qString);
        return workoutRecommendation;
    }

    public static String updateBodyPartQuery(List<BodyPart> bodyParts) {
        String bodyPartQuery = bodyParts.get(0).getEnglishName();
        for (int i = 1; i < bodyParts.size(); i++) {
            if (i == (bodyParts.size()-1)) bodyPartQuery = bodyPartQuery.concat(" and ").concat(bodyParts.get(i).getEnglishName());
            else bodyPartQuery = bodyPartQuery.concat(", ").concat(bodyParts.get(i).getEnglishName());
        }
        return bodyPartQuery;
    }

    public static String updateMachineQuery(List<Machine> machines) {
        if(machines == null) return null;
        String machineQuery = machines.get(0).getEnglishName();
        for (int i = 1; i < machines.size(); i++) {
            if (i == (machines.size()-1)) machineQuery = machineQuery.concat(" and ").concat(machines.get(i).getEnglishName());
            else machineQuery = machineQuery.concat(", ").concat(machines.get(i).getEnglishName());
        }
        machineQuery = machineQuery.concat(" for exercise equipment.");
        return machineQuery;
    }
}
