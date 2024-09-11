package FitMate.FitMateBackend.machine.entity;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.workout.entity.Workout;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class) // DB 생성 및 등록 날짜,시간 기록 가능하도록 추가
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Machine {

    @Id @GeneratedValue
    @Column(name = "machine_id")
    private Long id;

    private String koreanName;
    private String englishName;

    @JsonIgnore
    @ManyToMany(mappedBy = "machines")
    private List<BodyPart> bodyParts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "machines")
    private List<Workout> workouts = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public void update(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
    }
    public void removeWorkout(Workout workout) {
        this.workouts.remove(workout);
    }
    public void addBodyPart(BodyPart bodyPart) {
        this.bodyParts.add(bodyPart);
    }
    public void removeBodyPart(BodyPart bodyPart) {
        this.bodyParts.remove(bodyPart);
    }
}
