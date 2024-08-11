package FitMate.FitMateBackend.admin.bodypart;


import static FitMate.FitMateBackend.common.ApiResponseUtil.success;

import FitMate.FitMateBackend.common.ApiPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/bodypart")
@Slf4j(topic = "AdminBodyPartController")
public class AdminBodyPartController {

    private final AdminBodyPartService adminBodyPartService;

//    @PostMapping("/bodyParts") //운동부위 등록 (TEST 완료)
//    public ResponseEntity<String> saveBodyPart(@RequestBody BodyPartRequest request) {
//        return bodyPartService.saveBodyPart(request);
//    }
//
//    @GetMapping("/bodyParts/{bodyPartId}") //운동부위 단일조회 (TEST 완료)
//    public ResponseEntity<?> findBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
//        return bodyPartService.findOne(bodyPartId);
//    }
//

    @GetMapping("")
    public String readAll(@ModelAttribute ApiPageRequest request) {
        return success(adminBodyPartService.readAll(request.makePageable()));
    }
//
//    @PutMapping("/bodyParts/{bodyPartId}") //운동부위 수정 (TEST 완료)
//    public ResponseEntity<String> updateBodyPart(@PathVariable("bodyPartId") Long bodyPartId, @RequestBody BodyPartRequest request) {
//        return bodyPartService.updateBodyPart(bodyPartId, request);
//    }
//
//    @DeleteMapping("/bodyParts/{bodyPartId}") //운동부위 삭제 (TEST 완료)
//    public ResponseEntity<String> removeBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
//        return bodyPartService.removeBodyPart(bodyPartId);

}
