package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.ticket.models.Transaction;
import sapo.intern.mock.carstore.ticket.repositories.TransactionRepo;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepo transactionRepo;
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public int getTransactions(Date fromDate, Date toDate) {
        List<Transaction> transactions = transactionRepo.findAllPaidTransactionsBetweenDates(fromDate, toDate);
        return transactions.stream().mapToInt(Transaction::getAmount).sum();
    }
}
