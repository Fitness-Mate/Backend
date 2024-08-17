package FitMate.FitMateBackend.admin.supplement;

import static FitMate.FitMateBackend.common.ApiResponseUtil.success;

import FitMate.FitMateBackend.common.ApiPageRequest;
import FitMate.FitMateBackend.supplement.dto.SupplementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/supplement")
@Slf4j(topic = "AdminSupplementController")
public class AdminSupplementController {

    private final AdminSupplementService adminSupplementService;

    @PostMapping("")
    public String create(@Validated @ModelAttribute SupplementRequest request) {
        adminSupplementService.create(request);
        return success();
    }

    @GetMapping("")
    public String readAll(@ModelAttribute ApiPageRequest request) {
        return success(adminSupplementService.readAll(request.makePageable()));
    }

    @GetMapping("/{id}")
    public String read(@PathVariable("id") Long id) {
        return success(adminSupplementService.read(id));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminSupplementService.delete(id);
        return success();
    }
}
