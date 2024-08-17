package FitMate.FitMateBackend.chanhaleWorking.repository;

import FitMate.FitMateBackend.recommend.entity.RecommendedSupplement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendedSupplementRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(RecommendedSupplement recommendedSupplement){
        if (recommendedSupplement.getId() == null) {
            em.persist(recommendedSupplement);
        }else{
            em.merge(recommendedSupplement);
        }
    }
    public RecommendedSupplement findById(Long id) {
        RecommendedSupplement recommendedSupplement = em.find(RecommendedSupplement.class, id);
        return recommendedSupplement;
    }
}
