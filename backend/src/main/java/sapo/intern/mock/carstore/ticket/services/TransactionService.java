package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.event.TicketCompleted;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.ticket.dtos.AmountByDate;
import sapo.intern.mock.carstore.ticket.enums.TransactionStatus;
import sapo.intern.mock.carstore.ticket.models.Transaction;
import sapo.intern.mock.carstore.ticket.repositories.TicketRepo;
import sapo.intern.mock.carstore.ticket.repositories.TransactionRepo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepo transactionRepo;
    private final TicketRepo ticketRepo;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getTransactions(Integer page, Integer size) {
        return transactionRepo.findAll(PageRequest.of(page, size)).toList();
    }

    @EventListener(TicketCompleted.class)
    public void handleTicketCompleted(TicketCompleted event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        var ticketId = event.getTicketId();
        var ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new AppException(ErrorCode.TICKET_NOT_FOUND));
        var newTrans = new Transaction();
        newTrans.setAmount(ticket.getTotalAmount());
        newTrans.setCreatedDate(dateFormat.format(new Date(System.currentTimeMillis())));
        transactionRepo.save(newTrans);
    }



    public Integer getTransactionsAmount(String from, String to) {
        var sum = 0;
        for (var trans : transactionRepo.findAllPaidTransactionsBetweenDates(from, to) ) {
                sum+= (int) trans.getAmount();
        }
        return sum;
    }

    public List<AmountByDate> getStatistic() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        var currentDate = new Date(System.currentTimeMillis());
        var toDate = dateFormat.format(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date sevenDaysAgo = calendar.getTime();
        String fromDate = dateFormat.format(sevenDaysAgo);
        return transactionRepo.getStatistic(fromDate, toDate);
    }
}
