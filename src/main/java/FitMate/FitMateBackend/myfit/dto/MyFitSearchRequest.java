package FitMate.FitMateBackend.myfit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFitSearchRequest {
    private String searchKeyword;
}
