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
import java.util.Collections;

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

	// Overloading
	public List<Workout> findAll(int page) {
		int offset = (page - 1) * ServiceConst.PAGE_BATCH_SIZE;
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

	// Overloading
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
		int offset = (page - 1) * ServiceConst.PAGE_BATCH_SIZE;
		int limit = ServiceConst.PAGE_BATCH_SIZE;

		BooleanBuilder builder = new BooleanBuilder();

		// 검색 키워드 처리
		if (search.getSearchKeyword() != null) {
			String match = "[^\uAC00-\uD7A30-9a-zA-Z\u3131-\u314E\u314F-\u3163]";
			String[] keywords = search.getSearchKeyword().replaceAll(match, "*").split("\\*");

			for (String keyword : keywords) {
				if (hasText(keyword)) {
					builder.or(QWorkout.workout.koreanName.like("%" + keyword + "%"))
							.or(QWorkout.workout.englishName.like("%" + keyword + "%"));
				}
			}
		}

		// 신체 부위 처리
		if (search.getBodyPartKoreanName() != null) {
			BooleanBuilder bodyPartBuilder = new BooleanBuilder();
			for (String koreanName : search.getBodyPartKoreanName()) {
				BodyPart bodyPart = bodyPartService.findByKoreanName(koreanName);
				if (bodyPart != null) {
					bodyPartBuilder.or(QWorkout.workout.bodyParts.contains(bodyPart));
				}
			}
			builder.and(bodyPartBuilder);
		}

		// 키워드와 신체 부위 조건이 모두 없으면 전체 반환
		if (search.getSearchKeyword() == null && search.getBodyPartKoreanName() == null) {
			return findAll(page);
		}

		// 쿼리 실행
		QWorkout workout = QWorkout.workout;
		JPAQueryFactory query = new JPAQueryFactory(em);
		List<Workout> result = query.select(workout)
				.from(workout)
				.where(builder)
				.orderBy(workout.id.desc())
				.offset(offset)
				.limit(limit)
				.fetch();

		// 키워드 가중치 정렬
		if (search.getSearchKeyword() != null) {
			List<String> keywordSet = new ArrayList<>(Set.of(search.getSearchKeyword().split(" ")));
			List<WorkoutWeight> weightedList = result.stream()
					.map(w -> {
						int weight = keywordSet.stream()
								.mapToInt(keyword -> {
									int keywordWeight = 0;
									if (w.getKoreanName().startsWith(keyword + " ")) {
										keywordWeight += 3;
									}
									if (w.getKoreanName().endsWith(" " + keyword)) {
										keywordWeight += 2;
									}
									if (w.getKoreanName().contains(" " + keyword + " ")) {
										keywordWeight += 1;
									}
									return keywordWeight;
								})
								.sum();
						return new WorkoutWeight(w, weight);
					})
					.sorted((o1, o2) -> Integer.compare(o2.getWeight(), o1.getWeight()))
					.toList();

			return weightedList.stream()
					.map(WorkoutWeight::getWorkout)
					.toList();
		}

		return result;
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