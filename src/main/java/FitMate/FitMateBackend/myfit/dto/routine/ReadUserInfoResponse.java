package FitMate.FitMateBackend.myfit.dto.routine;

import FitMate.FitMateBackend.user.entity.BodyData;
import FitMate.FitMateBackend.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadUserInfoResponse {

    private String userName;
    private int userAge;
    private float weight;
    private float height;

    public ReadUserInfoResponse(User user) {
        this.userName = user.getUserName();
        this.userAge = user.getAge();

        BodyData bodyData = user.getBodyDataHistory().get(user.getBodyDataHistory().size() - 1);
        this.weight = bodyData.getWeight();
        this.height = bodyData.getHeight();
    }
}
