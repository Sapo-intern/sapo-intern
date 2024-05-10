package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.models.RepairService;

@Repository
public interface RepairServiceRepo extends JpaRepository<RepairService, Long> {
}
