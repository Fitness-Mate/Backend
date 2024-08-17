package FitMate.FitMateBackend.admin.machine;

import FitMate.FitMateBackend.machine.entity.Machine;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMachineRepository extends JpaRepository<Machine, Long> {

    Optional<Machine> findByKoreanNameOrEnglishName(@Param("koreanName") String koreanName, @Param("englishName") String englishName);
}
