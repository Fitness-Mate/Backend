package FitMate.FitMateBackend.admin.bodypart;

import FitMate.FitMateBackend.domain.BodyPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBodyPartRepository extends JpaRepository<BodyPart, Long> {

}
