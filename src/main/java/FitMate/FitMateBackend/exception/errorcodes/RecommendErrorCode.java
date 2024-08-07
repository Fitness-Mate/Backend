package FitMate.FitMateBackend.exception.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecommendErrorCode {

    RECOMMEND_NOT_FOUND_EXCEPTION("존재하지 않는 추천기록입니다.");

    private final String statusMessage;
}
