package FitMate.FitMateBackend.common.auth;

import FitMate.FitMateBackend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraClaims {
    private Long userId;

    public ExtraClaims(User user) {
        this.userId = user.getId();
    }
}
