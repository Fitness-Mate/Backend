package FitMate.FitMateBackend.recommend.entity;

import FitMate.FitMateBackend.user.entity.BodyData;
import FitMate.FitMateBackend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recommends")
@Getter
@Setter
@DiscriminatorColumn(name = "recommend_type")
public abstract class Recommendation {
    @Id
    @GeneratedValue
    @Column(name = "recommend_id")
    private Long id;

    private String recommendationType; // Workout, Supplement

    @Column(length = 3000)
    private String queryText; // 질문에 들어간 텍스트
    private LocalDate date = LocalDate.now();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_data_id")
    private BodyData bodyData;

    public void setUser(User user) {
        this.user = user;
    }


}
