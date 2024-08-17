package FitMate.FitMateBackend.supplement.controller;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementSearchForm;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementService;
import FitMate.FitMateBackend.supplement.dto.SupplementListResponse;
import FitMate.FitMateBackend.supplement.dto.SupplementResponse;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.supplement.entity.SupplementType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplements")
public class SupplementController {
    private final SupplementService supplementService;

    @GetMapping("/{supplementId}")
    public SupplementResponse getSingleSupplement(
        @PathVariable("supplementId") Long supplementId
    ){
        Supplement supplement = supplementService.findSupplementById(supplementId);

        if (supplement == null) {
            return new SupplementResponse();
        }

        return supplementService.makeSupplementDto(supplement);
    }

    @GetMapping("/list/{pageNum}")
    public List<SupplementListResponse> getSupplementList(@PathVariable("pageNum") Long pageNum){
        List<Supplement> supplementList = supplementService.getSupplementBatch(pageNum);
        List<SupplementListResponse> supplementDtoList = new ArrayList<>();

        for (Supplement supplement : supplementList) {
            supplementDtoList.add(new SupplementListResponse(supplement));
        }
        return supplementDtoList;
    }

    @GetMapping("/type")
    public List<SupplementType> getSupplementTypes() {
        return Arrays.stream(SupplementType.values()).toList();
    }

    @PostMapping("/search/list/{pageNum}")
    public List<SupplementListResponse> searchSupplements(@PathVariable("pageNum") Long page, @RequestBody SupplementSearchForm form){
        List<Supplement> supplementList = supplementService.searchSupplementBatch(page, form);
        List<SupplementListResponse> supplementListDtoList = new ArrayList<>();
        for (Supplement supplement : supplementList) {
            supplementListDtoList.add(new SupplementListResponse(supplement));
        }
        return supplementListDtoList;
    }
}
