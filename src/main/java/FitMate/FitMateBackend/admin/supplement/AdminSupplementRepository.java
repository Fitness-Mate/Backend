package FitMate.FitMateBackend.admin.supplement;

import FitMate.FitMateBackend.supplement.entity.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSupplementRepository extends JpaRepository<Supplement, Long> {

}
