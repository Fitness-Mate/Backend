package FitMate.FitMateBackend.admin.supplement;

import static FitMate.FitMateBackend.common.ApiResponseUtil.success;

import FitMate.FitMateBackend.common.ApiPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/supplement")
public class AdminSupplementController {

    private final AdminSupplementService adminSupplementService;

    @GetMapping("")
    public String readAll(@ModelAttribute ApiPageRequest request) {
        return success(adminSupplementService.readAll(request.makePageable()));
    }

//    @PostMapping
//    public String createSupplement(@ModelAttribute SupplementForm form) throws IOException {
//        log.info("supp={}", form.getKoreanName());
//        String errorMsg = form.validateFields();
//        if (!errorMsg.equals("ok"))
//            return errorMsg;
//        return supplementService.createSupplement(form).toString();
//    }
//
//    @GetMapping("/{supplementId}")
//    public SupplementDto getSingleSupplement(@PathVariable("supplementId") Long supplementId) throws MalformedURLException {
//        Supplement supplement = supplementService.findSupplementById(supplementId);
//        if (supplement == null) {
//            return new SupplementDto();
//        }
//        return supplementService.makeSupplementDto(supplement);
//    }
//
//    @PostMapping("/{supplementId}")
//    public String updateSupplement(SupplementForm supplementForm, @PathVariable("supplementId") Long supplementId) throws IOException {
//        String errorMsg = supplementForm.validateFields();
//        if (!errorMsg.equals("ok"))
//            return errorMsg;
//        return supplementService.updateSupplement(supplementId, supplementForm).toString();
//    }
//
//    @DeleteMapping("/{supplementId}")
//    public void deleteSupplement(@PathVariable("supplementId") Long supplementId) {
//        supplementService.deleteSupplement(supplementId);
//    }
//
//    @GetMapping("/type")
//    public List<SupplementType> getSupplementTypes() {
//
//        return Arrays.stream(SupplementType.values()).toList();
//    }
//    @DeleteMapping("/delete/all")
//    public void deleteAllSupplement() {
//        supplementService.deleteAllSupplement();
//    }
}
