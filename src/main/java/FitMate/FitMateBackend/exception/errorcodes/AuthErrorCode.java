package FitMate.FitMateBackend.exception.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {

    //Auth Exception
    AUTHENTICATION_EXCEPTION("로그인 정보가 일치하지 않습니다."),
    ALREADY_LOGOUT_EXCEPTION("이미 로그아웃 되었습니다.");

    private final String statusMessage;
}
