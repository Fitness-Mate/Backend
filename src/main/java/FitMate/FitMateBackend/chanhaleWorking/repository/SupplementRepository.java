package FitMate.FitMateBackend.chanhaleWorking.repository;

import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementFlavorDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementFlavorServingDto;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.supplement.entity.SupplementType;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SupplementRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Supplement supplement){
        if (supplement.getId() == null) {
            em.persist(supplement);
        }else{
            em.merge(supplement);
        }
    }

    public Supplement findById(Long id) {
        return em.find(Supplement.class, id);
    }

    public void deleteSupplement(Long id) {
        Supplement supplement = em.find(Supplement.class, id);
        if (!(supplement == null)) {
            em.remove(supplement);
        }
    }
    public List<Supplement> findAll(){
        return em.createQuery("select s from Supplement s", Supplement.class).getResultList();}

    public List<Supplement> getSupplementBatch(Long page) {
        return em.createQuery("select s from Supplement s where s.isCaptain = true order by s.id", Supplement.class)
                .setFirstResult((int) (ServiceConst.PAGE_BATCH_SIZE * (page - 1))).setMaxResults(ServiceConst.PAGE_BATCH_SIZE)
                .getResultList();
    }

    public List<Supplement> searchSupplement(Long page, List<SupplementType> typeList, String matchString) {
        if (typeList == null || typeList.size() == 0) {
            typeList = List.of(SupplementType.values());
        }
        //마이페이지 검색 기능에는 페이징을 적용하면 안돼서 아래 조건을 임의로 추가했습니다.. 기분 나쁘셨다면 진심으로 죄송합니다..
        List<Supplement> supplementList;
        if(page == -1) {
            supplementList = em.createQuery("select s from Supplement s where s.type in :typ and (s.koreanName like :str or s.flavor like :str or s.englishName like :str) order by s.id", Supplement.class)
                    .setParameter("str", "%"+matchString.trim()+"%")
                    .setParameter("typ", typeList)
                    .getResultList();
        } else {
            supplementList = em.createQuery("select s from Supplement s where s.type in :typ and (s.koreanName like :str or s.flavor like :str or s.englishName like :str) order by s.id", Supplement.class)
                    .setParameter("str", "%"+matchString.trim()+"%")
                    .setParameter("typ", typeList)
                    .setFirstResult((int) (ServiceConst.PAGE_BATCH_SIZE * (page - 1))).setMaxResults(ServiceConst.PAGE_BATCH_SIZE)
                    .getResultList();
        }

        log.info("{}",supplementList.size());
        return supplementList;
    }

    public List<SupplementFlavorDto> getSupplementLineup(Supplement supplement) {
        List<SupplementFlavorDto> result = new ArrayList<>();
        List<Supplement> supplementList = em.createQuery("select s from Supplement s where s.koreanName = :name order by s.flavor", Supplement.class)
                .setParameter("name", supplement.getKoreanName())
                .getResultList();
        String lastFlavor = "null";
        int idx = -1;
        for (Supplement supplement1 : supplementList) {
            if (lastFlavor.equals(supplement1.getFlavor())) {
                result.get(idx).getUnits().add(new SupplementFlavorServingDto(supplement1.getId(), supplement1.getServings(), supplement1.getPrice()));
            } else {
                result.add(new SupplementFlavorDto(supplement1.getFlavor()));
                idx ++;
                result.get(idx).getUnits().add(new SupplementFlavorServingDto(supplement1.getId(), supplement1.getServings(), supplement1.getPrice()));
                lastFlavor = supplement1.getFlavor();
            }
        }
        return result;
    }
    public List<Supplement> getAllCaptains() {
        List<Supplement> result = em.createQuery("select s from Supplement s where s.isCaptain = true", Supplement.class)
                .getResultList();
        log.info("true=[{}]", result.size());
        return result;
    }
}
