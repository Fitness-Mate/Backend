package FitMate.FitMateBackend.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutRequest {
    private String englishName;
    private String koreanName;
    private String videoLink;
    private String description;
    private List<Long> bodyPartIdList;
    private List<Long> machineIdList;
    private MultipartFile image;
}
