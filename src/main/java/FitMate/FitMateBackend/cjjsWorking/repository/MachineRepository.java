package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.machine.entity.QMachine;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MachineRepository {

    private final EntityManager em;
    private final BodyPartService bodyPartService;

    public void save(Machine machine) {
        em.persist(machine);
    }

    public Optional<Machine> findById(Long id) {
        return Optional.ofNullable(em.find(Machine.class, id));
    }

    public Optional<Machine> findByKoreanName(String koreanName) {
        return em.createQuery("select m from Machine m where m.koreanName = :koreanName", Machine.class)
                .setParameter("koreanName", koreanName)
                .getResultList().stream().findAny();
    }
    public Optional<Machine> findByEnglishName(String englishName) {
        return em.createQuery("select m from Machine m where m.englishName = :englishName", Machine.class)
                .setParameter("englishName", englishName)
                .getResultList().stream().findAny();
    }

    // Overloading
    public List<Machine> findAll() {
        return em.createQuery("select m from Machine m order by m.id desc", Machine.class)
                .getResultList();
    }
    public List<Machine> findAll(int page) {
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        return em.createQuery("select m from Machine m order by m.id desc", Machine.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    // Overloading

    public void remove(Machine machine) {
        em.remove(machine);
    }

    public List<Machine> findByMachineKoreanName(List<String> machineKoreanName) {
        //workout recommendation 을 위해 사용
        if (machineKoreanName == null) {
            return null;
        }
        List<Machine> machines = new ArrayList<>();
        for (String koreanName : machineKoreanName) {
            Machine machine = findByKoreanName(koreanName).get();
            machines.add(machine);
        }
        return machines;
    }

    public List<Machine> findWithBodyPart(List<String> bodyPartKoreanName) {
        QMachine machine = QMachine.machine;
        JPAQueryFactory query = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();
        for (String koreanName : bodyPartKoreanName) {
            BodyPart bodyPart = bodyPartService.findByKoreanName(koreanName);
            builder.or(QMachine.machine.bodyParts.contains(bodyPart));
        }

        return query
                .select(machine)
                .from(machine)
                .where(builder)
                .orderBy(machine.id.desc())
                .fetch();
    }
}
