package FitMate.FitMateBackend.myfit.dto.mySupplement;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementCreateRequest {
    List<Long> supplementIds = new ArrayList<>();
}
