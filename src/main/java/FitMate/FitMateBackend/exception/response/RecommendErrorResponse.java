package FitMate.FitMateBackend.exception.response;

import FitMate.FitMateBackend.exception.errorcodes.RecommendErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendErrorResponse {
    private RecommendErrorCode status;
    private String statusMessage;
}
