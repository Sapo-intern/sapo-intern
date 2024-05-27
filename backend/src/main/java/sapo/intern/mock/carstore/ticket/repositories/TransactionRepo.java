package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.models.Transaction;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.status = 'PAID' AND t.createdDate BETWEEN :startDate AND :endDate")
    List<Transaction> findAllPaidTransactionsBetweenDates(Date fromDate, Date toDate);
}
