package FitMate.FitMateBackend.myfit.controller;

import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.common.auth.JwtService;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.myfit.dto.routine.ReadUserInfoResponse;
import FitMate.FitMateBackend.myfit.dto.routine.RoutineReadAllResponse;
import FitMate.FitMateBackend.myfit.dto.routine.RoutineSetRequest;
import FitMate.FitMateBackend.myfit.dto.routine.SupplementRoutineUpdateRequest;
import FitMate.FitMateBackend.myfit.service.RoutineService;
import FitMate.FitMateBackend.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myfit")
public class RoutineController {

    private final RoutineService routineService;
    private final UserService userService;

    @GetMapping("/userInfo") //내 정보 요약 - 테스트 완료
    public ResponseEntity<ReadUserInfoResponse> readUserInfo(@RequestHeader HttpHeaders header) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        User user = userService.getUserWithId(userId);
        if(user == null)
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(new ReadUserInfoResponse(user));
    }

    @GetMapping("/routines/workout") //운동 루틴 목록 조회 - 테스트 완료
    public ResponseEntity<List<RoutineReadAllResponse>> readAllWorkoutRoutine(@RequestHeader HttpHeaders header) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        User user = userService.getUserWithId(userId);

        return ResponseEntity.ok(
                routineService.findAllWorkoutRoutineWithIndex(user.getId()).stream()
                        .map(RoutineReadAllResponse::new)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/routines/workout") //운동 루틴 관리 - 테스트 완료
    public void setWorkoutRoutine(@RequestHeader HttpHeaders header,
                                  @RequestBody RoutineSetRequest request) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        User user = userService.getUserWithId(userId);

        routineService.setWorkoutRoutines(user, request.getRoutines());
    }

    @PutMapping("/routines/supplement") //보조제 루틴 이름 수정 - 테스트 완료
    public void updateSupplementRoutineName(@RequestHeader HttpHeaders header,
                                            @RequestBody SupplementRoutineUpdateRequest request) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        routineService.updateSupplementRoutineName(userId, request);
    }
}