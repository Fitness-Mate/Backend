package FitMate.FitMateBackend.machine.entity;

import FitMate.FitMateBackend.machine.dto.MachineRequest;
import java.util.ArrayList;

public class MachineMapper {

    public static Machine toEntity(MachineRequest request, String imgFileName) {
        return Machine.builder()
            .koreanName(request.getKoreanName())
            .englishName(request.getEnglishName())
						.imgFileName(imgFileName)
            .workouts(new ArrayList<>())
            .bodyParts(new ArrayList<>())
            .build();
    }
}
