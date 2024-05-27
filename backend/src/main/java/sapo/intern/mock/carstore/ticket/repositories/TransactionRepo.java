package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.models.Transaction;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.status = TransactionStatus.PAID AND t.createdDate BETWEEN :startDate AND :endDate")
    List<Transaction> findAllPaidTransactionsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
