package FitMate.FitMateBackend.chanhaleWorking.repository;

import FitMate.FitMateBackend.user.entity.BodyData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BodyDataRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(BodyData bodyData){
        if (bodyData.getId() == null) {
            em.persist(bodyData);
        }else{
            em.merge(bodyData);
        }
    }

    public BodyData findById(Long id) {
        return em.find(BodyData.class, id);
    }

    public void deleteBodyData(Long id) {
        BodyData bodyData = em.find(BodyData.class, id);
        if (!(bodyData == null)) {
            em.remove(bodyData);
        }
    }

}
