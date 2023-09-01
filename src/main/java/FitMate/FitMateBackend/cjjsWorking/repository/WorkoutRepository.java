package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearch;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.QWorkout;
import FitMate.FitMateBackend.domain.Workout;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class WorkoutRepository {

    private final EntityManager em;
    private final BodyPartService bodyPartService;

    public void save(Workout workout) {
        em.persist(workout);
    }

    public Optional<Workout> findById(Long id) {
        return Optional.ofNullable(em.find(Workout.class, id));
    }
    public Optional<Workout> findByKoreanName(String koreanName) {
        return em.createQuery("select w from Workout w where w.koreanName = :koreanName", Workout.class)
                .setParameter("koreanName", koreanName)
                .getResultList()
                .stream().findAny();
    }
    public Optional<Workout> findByEnglishName(String englishName) {
        return em.createQuery("select w from Workout w where w.englishName = :englishName", Workout.class)
                .setParameter("englishName", englishName)
                .getResultList()
                .stream().findAny();
    }

    //Overloading
    public List<Workout> findAll(int page) {
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        return em.createQuery("select w from Workout w order by w.id desc", Workout.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Workout> findAll() {
        return em.createQuery("select w from Workout w order by w.id desc", Workout.class)
                .getResultList();
    }
    //Overloading

    public void remove(Workout workout) {
        em.remove(workout);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Workout> searchAll(int page, WorkoutSearch search) {
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        BooleanBuilder builder = new BooleanBuilder();
        Set<String> keywordSet = new HashSet<>();
        if(search.getSearchKeyword() != null) {
            //search keyword tokenization
            String match = "[^\uAC00-\uD7A30-9a-zA-Z]";
            String[] keywords = search.getSearchKeyword().replaceAll(match, "*").split("\\*");
            for (String keyword : keywords) { //remove blank and duplicate keywords
                if(hasText(keyword)) keywordSet.add(keyword);
            }
            for (String keyword : keywordSet) { //builder setting
                builder.or(QWorkout.workout.englishName.like("%" + keyword + "%"));
                builder.or(QWorkout.workout.koreanName.like("%" + keyword + "%"));
            }
        }
        if(search.getBodyPartKoreanName() != null) {
            for (String koreanName : search.getBodyPartKoreanName()) {
                BodyPart bodyPart = bodyPartService.findByKoreanName(koreanName);
                builder.and(QWorkout.workout.bodyParts.contains(bodyPart));
            }
        }

        QWorkout workout = QWorkout.workout;
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<Workout> result = query
                .select(workout)
                .from(workout)
                .where(builder)
                .orderBy(workout.id.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        if(search.getSearchKeyword() != null) { //keyword weight sorting
            List<WorkoutWeight> list = new ArrayList<>(result.stream().map(w -> {
                int weight = 0;
                for (String keyword : keywordSet) {
                    if (w.getEnglishName().contains(keyword)) weight += 1;
                    if (w.getKoreanName().contains(keyword)) weight += 1;
                }
                return new WorkoutWeight(w, weight);
            }).toList());
            list.sort((o1, o2) -> o2.getWeight() - o1.getWeight());

            return list.stream().map(WorkoutWeight::getWorkout).toList();
        } else {
            return result;
        }
    }

    static class WorkoutWeight {
        private Workout workout;
        private int weight;

        public WorkoutWeight(Workout workout, int weight) {
            this.workout = workout;
            this.weight = weight;
        }

        public Workout getWorkout() {
            return workout;
        }

        public int getWeight() {
            return weight;
        }
    }
}