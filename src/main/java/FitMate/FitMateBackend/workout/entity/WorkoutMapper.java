package FitMate.FitMateBackend.workout.entity;

import FitMate.FitMateBackend.workout.dto.WorkoutRequest;
import java.util.ArrayList;

public class WorkoutMapper {

    public static Workout toEntity(WorkoutRequest request, String imgFileName) {
        return Workout.builder()
            .koreanName(request.getKoreanName())
            .englishName(request.getEnglishName())
            .videoLink(request.getVideoLink())
            .description(request.getDescription())
						.atcetera(request.getAtcetera())
            .imgFileName(imgFileName)
            .bodyParts(new ArrayList<>())
            .machines(new ArrayList<>())
            .build();
    }

    public static void toResponse(Workout workout) {

    }
}
