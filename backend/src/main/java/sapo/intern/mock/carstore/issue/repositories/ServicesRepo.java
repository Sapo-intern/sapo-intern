package sapo.intern.mock.carstore.issue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.issue.models.ServiceModal;

@Repository
public interface ServicesRepo extends JpaRepository<ServiceModal, Long> {
    ServiceModal findByName(String name);
}
