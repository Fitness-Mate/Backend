package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.common.configuration.argumentresolver.Login;
import FitMate.FitMateBackend.chanhaleWorking.dto.BodyDataDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.UserArgResolverDto;
import FitMate.FitMateBackend.chanhaleWorking.form.bodyData.BodyDataForm;
import FitMate.FitMateBackend.chanhaleWorking.service.BodyDataService;
import FitMate.FitMateBackend.domain.BodyData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/bodyData")
public class BodyDataController {
    private final BodyDataService bodyDataService;

    @PostMapping
    public String createBodyData(@Login UserArgResolverDto userArgDto, @RequestBody BodyDataForm form) {
        if (form == null || userArgDto == null) {
            return "잘못된 입력";
        }
        String errMsg = form.validateFields();
        if (!errMsg.equals("ok")) {
            return errMsg;
        }
        return bodyDataService.createBodyData(userArgDto.getUserId(), form);
    }

    @GetMapping("/{bodyDataId}")
    public BodyDataDto getBodyData(@Login UserArgResolverDto userArgDto,@PathVariable("bodyDataId") Long bodyDataId) {
        BodyData bodyData = bodyDataService.getBodyData(bodyDataId);
        if (bodyData == null ||!Objects.equals(bodyData.getUser().getId(), userArgDto.getUserId())) {
            return new BodyDataDto();
        }
        return BodyDataDto.createBodyDataDto(bodyData);
    }

    @GetMapping("/list/{pageNum}")
    public List<BodyDataDto> getBodyDataBatch(@Login UserArgResolverDto userArgDto, @PathVariable("pageNum") Long pageNum) {
        List<BodyDataDto> result = new ArrayList<>();
        if (userArgDto == null || pageNum < 0) {
            return result;
        }
        List<BodyData> bodyDataList = bodyDataService.getBodyDataBatch(userArgDto.getUserId(), pageNum);
        for (BodyData bodyData : bodyDataList) {
            result.add(BodyDataDto.createBodyDataDto(bodyData));
        }
        return result;
    }

    @GetMapping("/recent")
    public BodyDataDto getRecentData(@Login UserArgResolverDto userArgDto) {

        return BodyDataDto.createBodyDataDto(bodyDataService.getRecentBodyData(userArgDto.getUserId()));
    }

    @PostMapping("/delete/{bodyDataId}")
    public String deleteBodyData(@Login UserArgResolverDto userArgDto,@PathVariable("bodyDataId") Long bodyDataId) {
        BodyData bodyData = bodyDataService.getBodyData(bodyDataId);
        if (bodyData == null ||!Objects.equals(bodyData.getUser().getId(), userArgDto.getUserId())) {
            return "fail";
        }
        bodyDataService.deleteBodyData(bodyDataId);
        return "ok";
    }
}
