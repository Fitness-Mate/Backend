package FitMate.FitMateBackend.admin.bodypart;

import FitMate.FitMateBackend.bodypart.entity.BodyPart;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBodyPartRepository extends JpaRepository<BodyPart, Long> {

    Optional<BodyPart> findByKoreanNameOrEnglishName(@Param("koreanName") String koreanName, @Param("englishName") String englishName);
}
