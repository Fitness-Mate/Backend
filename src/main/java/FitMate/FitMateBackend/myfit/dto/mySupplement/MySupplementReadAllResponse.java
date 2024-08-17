package FitMate.FitMateBackend.myfit.dto.mySupplement;

import FitMate.FitMateBackend.myfit.entity.MySupplement;
import FitMate.FitMateBackend.myfit.entity.Routine;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementReadAllResponse {
    private String routineName;
    private List<MySupplementReadData> supplements = new ArrayList<>();

    public MySupplementReadAllResponse(Routine routine, List<MySupplement> mySupplements) {
        this.routineName = routine.getRoutineName();
        for (MySupplement mySupplement : mySupplements) {
            this.supplements.add(new MySupplementReadData(mySupplement));
        }
    }
}
