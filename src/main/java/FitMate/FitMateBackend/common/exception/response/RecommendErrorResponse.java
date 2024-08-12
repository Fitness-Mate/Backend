package FitMate.FitMateBackend.common.exception.response;

import FitMate.FitMateBackend.common.exception.errorcodes.RecommendErrorCode;
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
