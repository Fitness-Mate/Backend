package FitMate.FitMateBackend.exception.response;

import FitMate.FitMateBackend.exception.errorcodes.JwtFilterErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtFilterErrorResponse {
    private JwtFilterErrorCode status;
    private String statusMessage;
}
