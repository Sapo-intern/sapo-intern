package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.models.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
}
