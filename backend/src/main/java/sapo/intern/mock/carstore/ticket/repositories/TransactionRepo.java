package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.intern.mock.carstore.ticket.dtos.AmountByDate;
import sapo.intern.mock.carstore.ticket.models.Transaction;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.createdDate BETWEEN :startDate AND :endDate")
    List<Transaction> findAllPaidTransactionsBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);
    @Query("SELECT new sapo.intern.mock.carstore.ticket.dtos.AmountByDate(t.createdDate, sum(t.amount)) FROM Transaction t WHERE t.createdDate BETWEEN :startDate AND :endDate group by t.createdDate")
    List<AmountByDate> getStatistic(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
