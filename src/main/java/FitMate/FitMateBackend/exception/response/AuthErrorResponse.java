package FitMate.FitMateBackend.exception.response;

import FitMate.FitMateBackend.exception.errorcodes.AuthErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthErrorResponse {
    private AuthErrorCode status;
    private String statusMessage;
}
