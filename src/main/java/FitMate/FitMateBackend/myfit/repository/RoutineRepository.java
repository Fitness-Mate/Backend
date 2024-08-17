package FitMate.FitMateBackend.myfit.repository;

import FitMate.FitMateBackend.myfit.entity.Routine;
import FitMate.FitMateBackend.myfit.entity.SupplementRoutine;
import FitMate.FitMateBackend.myfit.entity.WorkoutRoutine;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoutineRepository {

    private final EntityManager em;

    public Long save(Routine routine) {
        em.persist(routine);
        return routine.getId();
    }

    public Optional<Routine> findById(Long routineId) {
        return Optional.ofNullable(em.find(Routine.class, routineId));
    }

    public Optional<SupplementRoutine> findSupplementRoutineByUserId(Long userId) {
        return em.createQuery(
                "select r from SupplementRoutine r" +
                        " where r.user.id = :userId", SupplementRoutine.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream().findAny();
    }

    public List<WorkoutRoutine> findAllWorkoutRoutineWithIndex(Long userId) {
        return em.createQuery(
                "select w from WorkoutRoutine w" +
                        " where w.user.id = :userId" +
                        " order by w.routineIndex asc", WorkoutRoutine.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void remove(Routine routine) {
        em.remove(routine);
    }
}
