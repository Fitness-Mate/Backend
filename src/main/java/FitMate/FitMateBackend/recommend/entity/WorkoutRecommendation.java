package FitMate.FitMateBackend.recommend.entity;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.user.entity.BodyData;
import FitMate.FitMateBackend.user.entity.User;
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

	private String caution; // 운동에 대한 주의사항 저장 추가

	public static WorkoutRecommendation createWorkoutRecommendation(User user, List<BodyPart> bodyParts,
			List<Machine> machines, String workoutList, String caution) {

		// 주의사항이 null이거나 빈 문자열인 경우 예외 발생
		if (caution == null || caution.trim().isEmpty()) {
			throw new IllegalArgumentException("Caution (주의사항) is required and cannot be null or empty.");
		}

		WorkoutRecommendation workoutRecommendation = new WorkoutRecommendation();
		BodyData bodyData = user.getBodyDataHistory().get(user.getBodyDataHistory().size() - 1);
		workoutRecommendation.setBodyData(bodyData);
		workoutRecommendation.setUser(user);

		// bodyPart 필수선택
		String bodyPartString = "";
		for (int i = 0; i < bodyParts.size(); i++) {
			if (i == (bodyParts.size() - 1))
				bodyPartString = bodyPartString.concat(bodyParts.get(i).getKoreanName());
			else
				bodyPartString = bodyPartString.concat(bodyParts.get(i).getKoreanName()).concat(",");
		}
		workoutRecommendation.setRequestedBodyParts(bodyPartString);

		// machine 필수선택
		String machineString = "";
		for (int i = 0; i < machines.size(); i++) {
			if (i == (machines.size()) - 1)
				machineString = machineString.concat(machines.get(i).getKoreanName());
			else
				machineString = machineString.concat(machines.get(i).getKoreanName()).concat(",");
		}
		workoutRecommendation.setRequestedMachines(machineString);

		workoutRecommendation.setRecommendationType("Workout");
		workoutRecommendation.setCaution(caution); // 주의사항 설정

		String bodyPartQuery = updateBodyPartQuery(bodyParts);
		String machineQuery = updateMachineQuery(machines);

		String qString = "You have to recommend an exercise considering my gender, height, weight, body composition information,"
				+
				" and the exercise part and exercise equipment I want. I'm ";

		qString = qString.concat(user.getSex().equals("남성") ? "male, " : "female, ").concat(bodyData.describe());
		qString = qString.concat(" The part I want to exercise is my ");
		qString = qString.concat(bodyPartQuery).concat(" and I want to use a ");
		if (machineQuery != null)
			qString = qString.concat(machineQuery).concat("\n\n");

		qString = qString.concat("When answering, be sure to observe the requirements below.\n" +
				"1. Find a workout from the list below and recommend it.\n");
		qString = qString.concat(workoutList).concat("\n\n");
		qString = qString.concat("2. When answering, do not add any comments," +
				" and answer the ten exercises in the form " +
				"[workout index in list] [weight(kg)] [repeat] [set] in exactly ten lines, one line for each exercise. " +
				"Below is an example for you to answer\n" +
				"[99955][60kg][10][5]\n" +
				"[200005][30kg][10][5]\n" +
				"[50109][40kg][12][4]\n\n");
		qString = qString.concat("3. When recommending a weight, " +
				"please suggest an exact number (kg) instead of an ambiguous expression such as a medium weight.\n\n");
		qString = qString.concat("4. Only recommend exercises that use the exercise equipment I suggested. " +
				"The recommended exercise and the equipment used must match.\n\n");

		// 각 운동에 대한 주의사항 요청 추가
		qString = qString.concat(
				"5. Please provide meaningful precautions or safety guidelines related to exercise injuries in **Korean**, " +
						"which are easy for beginners to understand. Each guideline must contain at least 30 Korean characters " +
						"(excluding spaces) and no more than 50 characters, written in polite Korean language (높임말) to ensure " +
						"the tone is concise, friendly, and respectful. The precautions should address specific risks, proper " +
						"techniques, or injury prevention for each exercise.\n");
		qString = qString.concat("For example: \n");
		qString = qString.concat("[99955][60kg][10][5][로우 머신 사용 시 허리를 중립 자세로 유지하며 무게 중심을 발뒤꿈치에 두세요.]\n");
		qString = qString.concat("[50112][30kg][10][5][스쿼트 시 무릎이 발끝을 넘지 않도록 자세를 유지하세요.]\n");
		qString = qString.concat("[249962][35kg][12][4][머신 로우 사용 시 어깨가 들리지 않도록 집중하세요. 자세를 잘못하면 등 부상이 발생할 수 있습니다.]\n");
		qString = qString.concat("[99957][45kg][10][5][딥스 시 팔꿈치를 너무 벌리지 않도록 하여 어깨 관절의 부담을 줄이세요.]\n");
		qString = qString.concat("[99956][40kg][12][4][인클라인 벤치프레스 시 머리와 등을 벤치에 밀착하고 가슴을 펴주세요. 어깨 부상을 예방할 수 있습니다.]\n");
		qString = qString.concat("[150054][20kg][12][4][머신 숄더프레스 시 등받이에 등을 밀착시키고 어깨로만 밀어 올리세요. 허리에 무리가 가지 않도록 주의하세요.]\n");
		qString = qString.concat(
				"[workout index in list][weight(kg)][repeat][set][caution]\n");

		workoutRecommendation.setQueryText(qString);
		return workoutRecommendation;
	}

	public static String updateBodyPartQuery(List<BodyPart> bodyParts) {
		String bodyPartQuery = bodyParts.get(0).getEnglishName();
		for (int i = 1; i < bodyParts.size(); i++) {
			if (i == (bodyParts.size() - 1))
				bodyPartQuery = bodyPartQuery.concat(" and ").concat(bodyParts.get(i).getEnglishName());
			else
				bodyPartQuery = bodyPartQuery.concat(", ").concat(bodyParts.get(i).getEnglishName());
		}
		return bodyPartQuery;
	}

	public static String updateMachineQuery(List<Machine> machines) {
		if (machines == null)
			return null;
		String machineQuery = machines.get(0).getEnglishName();
		for (int i = 1; i < machines.size(); i++) {
			if (i == (machines.size() - 1))
				machineQuery = machineQuery.concat(" and ").concat(machines.get(i).getEnglishName());
			else
				machineQuery = machineQuery.concat(", ").concat(machines.get(i).getEnglishName());
		}
		machineQuery = machineQuery.concat(" for exercise equipment.");
		return machineQuery;
	}
}
