package FitMate.FitMateBackend.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ApiErrorCode apiErrorCode;
    private final String message;

    public ApiException(ApiErrorCode apiErrorCode) {
        super(apiErrorCode.getStatusMessage());

        this.apiErrorCode = apiErrorCode;
        this.message = apiErrorCode.getStatusMessage();
    }
}
