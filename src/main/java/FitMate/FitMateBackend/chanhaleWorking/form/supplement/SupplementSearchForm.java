package FitMate.FitMateBackend.chanhaleWorking.form.supplement;

import FitMate.FitMateBackend.supplement.entity.SupplementType;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class SupplementSearchForm {
    private String searchKeyword;
    private List<SupplementType> supplementType;
}
