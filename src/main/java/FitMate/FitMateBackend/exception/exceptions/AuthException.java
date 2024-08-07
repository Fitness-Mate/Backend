package FitMate.FitMateBackend.exception.exceptions;

import FitMate.FitMateBackend.exception.errorcodes.AuthErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private AuthErrorCode authErrorCode;
    private String message;

    public AuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getStatusMessage());
        this.authErrorCode = authErrorCode;
        this.message = authErrorCode.getStatusMessage();
    }
}
