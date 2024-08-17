package FitMate.FitMateBackend.chanhaleWorking.controller;


import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementRecommendationDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementRecommendationListDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.UserArgResolverDto;
import FitMate.FitMateBackend.chanhaleWorking.form.recommendation.SupplementRecommendationForm;
import FitMate.FitMateBackend.chanhaleWorking.service.ChatGptService;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementRecommendationService;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementService;
import FitMate.FitMateBackend.common.configuration.argumentresolver.Login;
import FitMate.FitMateBackend.recommend.entity.Purpose;
import FitMate.FitMateBackend.recommend.entity.SupplementRecommendation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/recommendation/supplement")
@RequiredArgsConstructor
public class SupplementRecommendationController {
    private final SupplementRecommendationService supplementRecommendationService;
    private final SupplementService supplementService;
    private final ChatGptService chatGptService;

    @PostMapping
    public Long getRecommendedText(@Login UserArgResolverDto userArgDto, @RequestBody SupplementRecommendationForm form) throws Exception {
        Long recommendationId = supplementRecommendationService.createSupplementRecommendation(userArgDto.getUserId(), form);
        log.info("purpose:[{}][{}]", form.getMonthlyBudget(), form.getPurpose().get(0));
        String question = "I have these supplements. ".concat(supplementService.getSupplementString());
        SupplementRecommendation supplementRecommendation = supplementRecommendationService.findById(userArgDto.getUserId(), recommendationId);
        question = question.concat(supplementRecommendation.getQueryText());
        log.info(question);
        chatGptService.sendRequest(userArgDto.getUserId(), supplementRecommendation.getId(), question);
        return recommendationId;
    }

    @GetMapping("/history/{supplementRecommendationId}")
    public SupplementRecommendationDto getRecommendation(@Login UserArgResolverDto userArgDto, @PathVariable("supplementRecommendationId") Long supplementRecommendationId) throws Exception {
        SupplementRecommendation sr = supplementRecommendationService.getSupplementRecommendation(userArgDto.getUserId(), supplementRecommendationId);
        if (sr == null) {
            return new SupplementRecommendationDto();
        }
        // 로그인 유저가 해당 supplementRecommendation 의 소유자가 아님.
        if (!Objects.equals(sr.getBodyData().getUser().getId(), userArgDto.getUserId())) {
            return new SupplementRecommendationDto();
        }
        return supplementRecommendationService.createSupplementRecommendationDto(sr);
    }

    @GetMapping("/history/list/{pageNum}")
    public List<SupplementRecommendationListDto> getRecommendationBatch(@Login UserArgResolverDto userArgDto, @PathVariable("pageNum") Long pageNum) throws Exception {
        // User 소유의 supplementRecommendation 인지 확인하는 기능 필요
        List<SupplementRecommendationListDto> result = new ArrayList<>();
        List<SupplementRecommendation> sList = supplementRecommendationService.getSupplementRecommendationBatch(userArgDto.getUserId(), pageNum);
        for (SupplementRecommendation supplementRecommendation : sList) {
            result.add(SupplementRecommendationListDto.createSupplementRecommendationDto(supplementRecommendation));
        }
        return result;
    }

    @GetMapping("/purposes")
    public List<String> getPurposes() {
        List<String> result = new ArrayList<>();
        for (Purpose p : Purpose.values()) {
            result.add(p.name());
        }
        return result;
    }

    @GetMapping("/test")
    public String test(){
        return supplementService.getSupplementString();
    }
}
