package FitMate.FitMateBackend.bodypart.repository;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BodyPartRepository {

    private final EntityManager em;

    public void save(BodyPart bodyPart) {
        em.persist(bodyPart);
    }

    public Optional<BodyPart> findById(Long id) {
        return Optional.ofNullable(em.find(BodyPart.class, id));
    }

    public Optional<BodyPart> findByKoreanName(String koreanName) {
        return em.createQuery("select b from BodyPart b where b.koreanName = :koreanName", BodyPart.class)
                .setParameter("koreanName", koreanName)
                .getResultList()
                .stream().findAny();
    }
    public Optional<BodyPart> findByEnglishName(String englishName) {
        return em.createQuery("select b from BodyPart b where b.englishName = :englishName", BodyPart.class)
                .setParameter("englishName", englishName)
                .getResultList()
                .stream().findAny();
    }

    //Overloading
    public List<BodyPart> findAll() {
        return em.createQuery("select b from BodyPart b", BodyPart.class)
                .getResultList();
    }
    public List<BodyPart> findAll(int page) {
        int offset = (page-1) * ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        return em.createQuery("select b from BodyPart b", BodyPart.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    //Overloading

    public void remove(BodyPart bodyPart) {
        em.remove(bodyPart);
    }

    public List<BodyPart> findByBodyPartKoreanName(List<String> bodyPartKoreanName) {
        //workout recommendation 에서 사용됨.
        List<BodyPart> bodyParts = new ArrayList<>();
        for (String koreanName : bodyPartKoreanName) {
            BodyPart bodyPart = findByKoreanName(koreanName).get();
            bodyParts.add(bodyPart);
        }
        return bodyParts;
    }
}
