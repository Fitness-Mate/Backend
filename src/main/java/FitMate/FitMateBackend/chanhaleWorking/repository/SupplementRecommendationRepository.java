package FitMate.FitMateBackend.chanhaleWorking.repository;

import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.recommend.entity.Recommendation;
import FitMate.FitMateBackend.recommend.entity.SupplementRecommendation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SupplementRecommendationRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Recommendation recommendation){
        if (recommendation.getId() == null) {
            em.persist(recommendation);
        }else{
            em.merge(recommendation);
        }
    }
    public SupplementRecommendation findById(Long userId, Long recId) {
        SupplementRecommendation recommendation = em.find(SupplementRecommendation.class, recId);
        if ( Objects.equals(recommendation.getBodyData().getUser().getId(), userId)) {
            return recommendation;
        }
        return null;

    }

    public List<SupplementRecommendation> getBatch(Long userId, Long pageNum) {
        return em.createQuery("select sr from SupplementRecommendation sr where sr.user.id = :userId", SupplementRecommendation.class)
                .setParameter("userId", userId)
                .setFirstResult((int) (ServiceConst.PAGE_BATCH_SIZE * (pageNum - 1)))
                .setMaxResults(ServiceConst.PAGE_BATCH_SIZE)
                .getResultList();
    }

}
