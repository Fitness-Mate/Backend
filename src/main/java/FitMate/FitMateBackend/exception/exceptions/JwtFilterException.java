package FitMate.FitMateBackend.exception.exceptions;

import FitMate.FitMateBackend.exception.errorcodes.JwtFilterErrorCode;
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
