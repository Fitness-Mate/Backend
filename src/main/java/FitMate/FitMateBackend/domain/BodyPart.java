package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name="body_part")
@Getter
@NoArgsConstructor
public class BodyPart {

    @Id @GeneratedValue
    @Column(name = "body_part_id")
    private Long id;

    @ManyToMany(mappedBy = "bodyParts")
    private List<Workout> workouts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "body_part_machine",
        joinColumns = @JoinColumn(name = "body_part_id"),
        inverseJoinColumns = @JoinColumn(name ="machine_id"))
    private List<Machine> machines = new ArrayList<>();

    private String englishName;
    private String koreanName;

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

    public void addMachine(Machine machine) {
        machines.add(machine);
    }
    public void removeMachine(Machine machine) {
        machines.remove(machine);
    }
    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }
    public void removeWorkout(Workout workout) {
        workouts.remove(workout);
    }
}