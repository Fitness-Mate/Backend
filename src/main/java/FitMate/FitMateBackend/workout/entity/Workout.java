package FitMate.FitMateBackend.workout.entity;

import FitMate.FitMateBackend.workout.dto.WorkoutRequest;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "workout_body_part",
        joinColumns = @JoinColumn(name = "workout_id"),
        inverseJoinColumns = @JoinColumn(name = "body_part_id")
    )
    private List<BodyPart> bodyParts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "workout_machine",
        joinColumns = @JoinColumn(name = "workout_id"),
        inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private List<Machine> machines = new ArrayList<>();

    private String englishName;
    private String koreanName;
    private String videoLink;
    private String description;
    private String imgFileName;

    public void update(WorkoutRequest form, String imgFileName) {
        this.englishName = form.getEnglishName();
        this.koreanName = form.getKoreanName();
        this.videoLink = form.getVideoLink();
        this.description = form.getDescription();
        this.imgFileName = imgFileName;
    }

    public void addBodypart(BodyPart bodyPart) {
        bodyParts.add(bodyPart);
    }
    public void addMachine(Machine machine) {
        machines.add(machine);
    }

    public void removeMachine(Machine machine) {
        this.machines.remove(machine);
    }
}
