package FitMate.FitMateBackend.common.exception.response;

import FitMate.FitMateBackend.common.exception.errorcodes.JwtFilterErrorCode;
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
