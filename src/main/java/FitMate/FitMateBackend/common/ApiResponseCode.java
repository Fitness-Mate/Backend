package FitMate.FitMateBackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {

    SUCCESS(1000)
    ;

    private final Integer code;
}
