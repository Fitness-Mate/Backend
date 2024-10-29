package FitMate.FitMateBackend.workout.repository;

import static org.springframework.util.StringUtils.hasText;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import FitMate.FitMateBackend.bodypart.service.BodyPartService;
import FitMate.FitMateBackend.common.constraint.ServiceConst;
import FitMate.FitMateBackend.machine.entity.Machine;
import FitMate.FitMateBackend.workout.dto.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.entity.QWorkout;
import FitMate.FitMateBackend.workout.entity.Workout;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public List<Workout> findAllWithBodyPartsAndMachines(List<BodyPart> bodyParts, List<Machine> machines) {
        BooleanBuilder builder = new BooleanBuilder();

        for (BodyPart bodyPart : bodyParts) {
            builder.or(QWorkout.workout.bodyParts.contains(bodyPart));
        }

        QWorkout workout = QWorkout.workout;
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(workout)
                .from(workout)
                .where(builder)
                .orderBy(workout.id.desc())
                .fetch();
    }

    public void remove(Workout workout) {
        em.remove(workout);
    }

		// 영어, 숫자, 특수 문자를 제거하고 한글만 남기기
		private String sanitizeKeyword(String keyword) {
			return keyword.replaceAll("[^가-힣\\s]", ""); // 한글과 공백 제외한 모든 문자 제거
		}


    public List<Workout> searchAll(int page, WorkoutSearchCond search) {
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        BooleanBuilder builder = new BooleanBuilder();
        Set<String> keywordSet = new HashSet<>();
        if(search.getSearchKeyword() != null) {
            //search keyword tokenization
            String match = "[^\uAC00-\uD7A30-9a-zA-Z\u3131-\u314E\u314F-\u3163]";
            String[] keywords = search.getSearchKeyword().replaceAll(match, "*").split("\\*");
            
						for (String keyword : keywords) { // 특수 문자 제거 후 blank 및 duplicate 제거
							String sanitizedKeyword = sanitizeKeyword(keyword); // 특수 문자 제거
              if (hasText(sanitizedKeyword)) keywordSet.add(sanitizedKeyword);
            }
            // contains 조건으로 각 키워드를 독립적인 단어로 포함하는지 확인
						for (String keyword : keywordSet) {
							builder.or(
                QWorkout.workout.koreanName.startsWith(keyword + " ") // 시작에 단독으로 위치한 경우
								.or(QWorkout.workout.koreanName.endsWith(" " + keyword)) // 끝에 단독으로 위치한 경우
								.or(QWorkout.workout.koreanName.contains(" " + keyword + " ")) // 중간에 공백으로 구분된 경우
              );
            }
        }
        if(search.getBodyPartKoreanName() != null) {
            for (String koreanName : search.getBodyPartKoreanName()) {
                BodyPart bodyPart = bodyPartService.findByKoreanName(koreanName);
                builder.or(QWorkout.workout.bodyParts.contains(bodyPart));
            }
        }

        QWorkout workout = QWorkout.workout;
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<Workout> result;

        if(page == -1) {
            result = query
                    .select(workout)
                    .from(workout)
                    .where(builder)
                    .orderBy(workout.id.desc())
                    .fetch();
        } else {
            result = query
                    .select(workout)
                    .from(workout)
                    .where(builder)
                    .orderBy(workout.id.desc())
                    .offset(offset)
                    .limit(limit)
                    .fetch();
        }

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