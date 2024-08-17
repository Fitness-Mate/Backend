package FitMate.FitMateBackend.myfit.entity;

import FitMate.FitMateBackend.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn //default: DTYPE
public abstract class Routine {

    @Id @GeneratedValue
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private final List<MyFit> myFit = new ArrayList<>();

    private String routineName;
    private int routineIndex;

    public Routine() {}
    public Routine(User user, String routineName, int routineIndex) {
        this.user = user;
        this.routineName = routineName;
        this.routineIndex = routineIndex;
    }

    public void update(String routineName, int routineIndex) {
        this.routineName = routineName;
        this.routineIndex = routineIndex;
    }
}
