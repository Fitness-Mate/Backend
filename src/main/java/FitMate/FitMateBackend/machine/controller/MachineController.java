package FitMate.FitMateBackend.machine.controller;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.UserMachineRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.UserMachineResponse;
import FitMate.FitMateBackend.machine.service.MachineService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @PostMapping("machines/list") //부위별 조회 (TEST 완료)
    public ResponseEntity<List<UserMachineResponse>> findMachines(@RequestBody UserMachineRequest request) {
        return ResponseEntity.ok(
                machineService.findWithBodyPart(request.getBodyPartKoreanName()).stream()
                .map(UserMachineResponse::new)
                .collect(Collectors.toList()));
    }
}