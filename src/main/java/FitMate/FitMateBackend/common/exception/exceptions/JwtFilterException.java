package FitMate.FitMateBackend.common.exception.exceptions;

import FitMate.FitMateBackend.common.exception.errorcodes.JwtFilterErrorCode;
import lombok.Getter;

@Getter
public class JwtFilterException extends RuntimeException {

    private JwtFilterErrorCode jwtFilterErrorCode;
    private String message;

    public JwtFilterException(JwtFilterErrorCode jwtFilterErrorCode) {
        super(jwtFilterErrorCode.getStatusMessage());
        this.jwtFilterErrorCode = jwtFilterErrorCode;
        this.message = jwtFilterErrorCode.getStatusMessage();
    }
}
