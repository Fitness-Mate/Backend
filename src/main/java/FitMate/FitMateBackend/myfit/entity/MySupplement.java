package FitMate.FitMateBackend.myfit.entity;

import FitMate.FitMateBackend.myfit.dto.mySupplement.MySupplementUpdateRequest;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MySupplement extends MyFit {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplement_id")
    private Supplement supplement;


    public MySupplement(Routine routine, Supplement supplement, int myFitIndex) {
        super(routine, myFitIndex);
        this.supplement = supplement;
    }

    public void update(MySupplementUpdateRequest request) {
        this.setMyFitIndex(request.getMySupplementIndex());
    }
}