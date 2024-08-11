package FitMate.FitMateBackend.admin.machine;

import static FitMate.FitMateBackend.common.ApiResponseUtil.success;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.common.ApiPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/machine")
public class AdminMachineController {

    private final AdminMachineService adminMachineService;

    @PostMapping("")
    public String saveMachine(@RequestBody MachineRequest request) {
        adminMachineService.create(request);
        return success();
    }

    @GetMapping("")
    public String readAll(@ModelAttribute ApiPageRequest request) {
        return success(adminMachineService.readAll(request.makePageable()));
    }

//    @PutMapping("/machines/{machineId}") //운동기구 수정 (TEST 완료)
//    public ResponseEntity<String> updateMachine(@PathVariable("machineId") Long machineId, @RequestBody MachineRequest request) {
//        return machineService.updateMachine(machineId, request);
//    }
//
//    @DeleteMapping("/machines/{machineId}") //운동기구 삭제 (TEST 완료)
//    public ResponseEntity<String> deleteMachine(@PathVariable("machineId") Long machineId) {
//        return machineService.removeMachine(machineId);
//    }

}
