package FitMate.FitMateBackend.myfit.entity;

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
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn //default: DTYPE
public abstract class MyFit {

    @Id @GeneratedValue
    @Column(name = "my_fit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    private int myFitIndex;

    public MyFit() {}
    public MyFit(Routine routine, int myFitIndex) {
        this.routine = routine;
        this.myFitIndex = myFitIndex;
    }

    public void setMyFitIndex(int myFitIndex) {
        this.myFitIndex = myFitIndex;
    }

    public void upMyFitIndex() {
        this.myFitIndex++;
    }

    public void downMyFitIndex() {
        this.myFitIndex--;
    }
}