package FitMate.FitMateBackend.common.exception.errorcodes;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    //Domain Exception - CustomException
    BODY_PART_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동 부위입니다. 이름을 확인해주세요."),
    BODY_PART_NOT_FOUND_EXCEPTION("존재하지 않는 운동 부위입니다."),

    MACHINE_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동기구입니다. 이름을 확인해주세요."),
    MACHINE_NOT_FOUND_EXCEPTION("존재하지 않는 운동기구입니다."),

    WORKOUT_ALREADY_EXIST_EXCEPTION("이미 존재하는 운동입니다. 이름을 확인해주세요."),
    WORKOUT_NOT_FOUND_EXCEPTION("존재하지 않는 운동입니다."),

    SUPPLEMENT_NOT_FOUND_EXCEPTION("존재하지 않는 보조제입니다."),

    USER_NOT_FOUND_EXCEPTION("존재하지 않는 유저입니다."),

    ROUTINE_NOT_FOUND_EXCEPTION("존재하지 않는 운동루틴입니다."),
    ROUTINE_SIZE_OVER_EXCEPTION("등록할 수 있는 운동루틴의 갯수는 " + ServiceConst.ROUTINE_MAX_SIZE + "개입니다."),

    MY_WORKOUT_SIZE_OVER_EXCEPTION("등록할 수 있는 운동의 갯수는 " + ServiceConst.MY_WORKOUT_MAX_SIZE +"개입니다."),
    MY_WORKOUT_NOT_FOUND_EXCEPTION("존재하지 않는 내 운동입니다."),
    ALREADY_EXIST_MY_WORKOUT_EXCEPTION("루틴에 이미 존재하는 내 운동입니다."),

    MY_SUPPLEMENT_SIZE_OVER_EXCEPTION("등록할 수 있는 보조제의 갯수는 " + ServiceConst.MY_SUPPLEMENT_MAX_SIZE +"개입니다."),
    MY_SUPPLEMENT_NOT_FOUND_EXCEPTION("존재하지 않는 내 보조제입니다."),
    ALREADY_EXIST_MY_SUPPLEMENT_EXCEPTION("루틴에 이미 존재하는 내 보조제입니다."),

    PAGE_NOT_FOUND_EXCEPTION("존재하지 않는 페이지입니다.");

    private final String statusMessage;
}
