package FitMate.FitMateBackend.admin.machine;

import FitMate.FitMateBackend.machine.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMachineRepository extends JpaRepository<Machine, Long> {

}
