package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.dtos.TicketStatistic;
import sapo.intern.mock.carstore.ticket.models.Ticket;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
    @Query("SELECT new sapo.intern.mock.carstore.ticket.dtos.TicketStatistic(sum(t.id), t.completeDate) FROM Ticket t WHERE t.status = sapo.intern.mock.carstore.ticket.enums.TicketStatus.PAID and t.createdDate BETWEEN :startDate AND :endDate group by t.completeDate")
    List<TicketStatistic> getStatistic(@Param("startDate") Date fromDate, @Param("endDate") Date toDate);
}
