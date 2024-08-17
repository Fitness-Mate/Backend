package FitMate.FitMateBackend.common;

import com.google.gson.Gson;

public class ApiResponseUtil {

    private static final Gson gson = new Gson();

    public static String success() {
        return success(null);
    }

    public static String success(Object data) {
        ApiResponse response = ApiResponse.builder()
            .code(ApiResponseCode.SUCCESS.getCode())
            .data(data)
            .build();

        return gson.toJson(response);
    }

    public static String fail(Object data) {
        ApiResponse response = ApiResponse.builder()
            .code(ApiResponseCode.FAIL.getCode())
            .data(data)
            .build();

        return gson.toJson(response);
    }
}
