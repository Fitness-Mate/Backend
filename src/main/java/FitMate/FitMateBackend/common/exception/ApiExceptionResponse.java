package FitMate.FitMateBackend.common.exception;

import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {
    private CustomErrorCode status;
    private String statusMessage;
}
