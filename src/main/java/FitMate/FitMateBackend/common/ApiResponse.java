package FitMate.FitMateBackend.common;

import lombok.Builder;

@Builder
public class ApiResponse {
    private Integer code;
    private Object data;
}
