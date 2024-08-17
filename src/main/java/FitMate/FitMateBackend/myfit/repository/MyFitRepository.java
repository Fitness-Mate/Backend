package FitMate.FitMateBackend.myfit.repository;

import FitMate.FitMateBackend.myfit.entity.MyFit;
import FitMate.FitMateBackend.myfit.entity.MySupplement;
import FitMate.FitMateBackend.myfit.entity.MyWorkout;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyFitRepository {

    private final EntityManager em;

    public void save(MyFit myFit) {
        em.persist(myFit);
    }

    public Optional<MyWorkout> findMyWorkoutById(Long myWorkoutId) {
        return Optional.ofNullable(em.find(MyWorkout.class, myWorkoutId));
    }

    public Optional<MySupplement> findMySupplementById(Long mySupplementId) {
        return Optional.ofNullable(em.find(MySupplement.class, mySupplementId));
    }

    public List<MyWorkout> findAllMyWorkoutWithRoutineId(Long routineId) {
        return em.createQuery(
                "select w from MyWorkout w" +
                        " where w.routine.id = :routineId" +
                        " order by w.myFitIndex asc", MyWorkout.class)
                .setParameter("routineId", routineId)
                .getResultList();
    }

    public List<MySupplement> findAllMySupplementWithRoutineId(Long routineId) {
        return em.createQuery(
                "select s from MySupplement s" +
                        " where s.routine.id = :routineId" +
                        " order by s.myFitIndex asc", MySupplement.class)
                .setParameter("routineId", routineId)
                .getResultList();
    }

    public void remove(MyFit myFit) {
        em.remove(myFit);
    }
}
