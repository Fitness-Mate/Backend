package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.user.entity.BodyData;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * history 그래프 조회 또는 recommendation 화면에 노출 될 bodyData
 *
 */
@Getter
@Data
@NoArgsConstructor
public class BodyDataDto {

    private Long bodyDataId;
    private LocalDate date;
    private Float height;
    private Float weight;
    private Float BodyFat;
    private Float MuscleMass;
    private Float upDownBalance;


    public static BodyDataDto createBodyDataDto(BodyData bodyData) {
        BodyDataDto bodyDataDto = new BodyDataDto();
        if (bodyData != null) {
            bodyDataDto.bodyDataId = bodyData.getId();
            bodyDataDto.date = bodyData.getDate();
            bodyDataDto.height = bodyData.getHeight();
            bodyDataDto.weight = bodyData.getWeight();
            bodyDataDto.BodyFat = bodyData.getBodyFat();
            bodyDataDto.MuscleMass = bodyData.getMuscleMass();
            bodyDataDto.upDownBalance = bodyData.getUpDownBalance();
        }
        return bodyDataDto;
    }
}
