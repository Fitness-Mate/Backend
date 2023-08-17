package FitMate.FitMateBackend.cjjsWorking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    //Jwt Filter Exception - Jwt Token 값이 변조되었을때 발생, 변경된 부분마다 다른 에러가 발생한다.
    MALFORMED_JWT_EXCEPTION("[손상된 토큰] 잘못된 요청입니다."),
    UNSUPPORTED_JWT_EXCEPTION("[지원하지 않는 토큰] 잘못된 요청입니다."),
    SIGNATURE_EXCEPTION("[지원하지 않는 토큰] 잘못된 요청입니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("[지원하지 않는 토큰] 잘못된 요청입니다."),

    EXPIRED_ACCESS_TOKEN_EXCEPTION("[만료된 접근 토큰] 토큰을 다시 발급받아주세요."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION("[만료된 토큰] 잘못된 요청입니다. 다시 로그인해주세요."),

    NON_START_BEARER_EXCEPTION("[잘못된 형식의 토큰] 클라이언트 토큰 요청 형식이 잘못되었습니다."),

    //Jwt Token Exception
    JWT_NOT_FOUND_EXCEPTION("[토큰을 찾을 수 없음] 접근 권한이 없습니다. 로그인 해주세요."), //토큰 없이 접근할때 발생

    //Auth Exception
    AUTHENTICATION_EXCEPTION("로그인 정보가 일치하지 않습니다."),

    //Domain Exception - CustomException
    BODY_PART_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동 부위입니다. 이름을 확인해주세요."),
    BODY_PART_NOT_FOUND_EXCEPTION("존재하지 않는 운동 부위입니다."),

    MACHINE_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동기구입니다. 이름을 확인해주세요."),
    MACHINE_NOT_FOUND_EXCEPTION("존재하지 않는 운동기구입니다."),

    WORKOUT_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동입니다. 이름을 확인해주세요."),
    WORKOUT_NOT_FOUND_EXCEPTION("존재하지 않는 운동입니다."),

    USER_NOT_FOUND_EXCEPTION("존재하지 않는 유저입니다."),

    PAGE_NOT_FOUND_EXCEPTION("존재하지 않는 페이지입니다.");

    private final String statusMessage;
}