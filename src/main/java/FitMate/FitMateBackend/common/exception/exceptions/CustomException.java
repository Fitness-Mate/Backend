package FitMate.FitMateBackend.common.exception.exceptions;

import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
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
