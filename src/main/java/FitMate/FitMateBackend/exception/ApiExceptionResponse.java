package FitMate.FitMateBackend.exception;

import FitMate.FitMateBackend.exception.errorcodes.CustomErrorCode;
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
