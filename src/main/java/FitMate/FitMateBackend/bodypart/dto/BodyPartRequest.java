package FitMate.FitMateBackend.bodypart.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyPartRequest {
    private String englishName;
    private String koreanName;
		private MultipartFile image;
}