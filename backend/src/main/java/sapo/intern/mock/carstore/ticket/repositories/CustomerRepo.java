package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.models.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
