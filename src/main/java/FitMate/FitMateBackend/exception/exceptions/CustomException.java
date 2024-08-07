package FitMate.FitMateBackend.exception.exceptions;

import FitMate.FitMateBackend.exception.errorcodes.CustomErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private CustomErrorCode customErrorCode;
    private String message;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage());
        this.customErrorCode = customErrorCode;
        this.message = customErrorCode.getStatusMessage();
    }
}
