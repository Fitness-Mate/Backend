package FitMate.FitMateBackend.workout.entity;

import FitMate.FitMateBackend.workout.dto.WorkoutRequest;

public class WorkoutMapper {

    public static Workout toEntity(WorkoutRequest request, String imgFileName) {
        return Workout.builder()
            .englishName(request.getEnglishName())
            .koreanName(request.getKoreanName())
            .videoLink(request.getVideoLink())
            .description(request.getDescription())
            .imgFileName(imgFileName)
            .build();
    }

    public static void toResponse(Workout workout) {

    }
}
