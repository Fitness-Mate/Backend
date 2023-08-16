package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartsResponse;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserBodyPartController {

    private final BodyPartService bodyPartService;

    @GetMapping("bodyParts/all") // 전체조회 (TEST 완료)
    public ResponseEntity<BodyPartsResponse> findBodyParts() {
        return ResponseEntity.ok(new BodyPartsResponse(bodyPartService.findAll()));
    }
}
