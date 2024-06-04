package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.event.TicketCanceled;
import sapo.intern.mock.carstore.event.TicketCompleted;
import sapo.intern.mock.carstore.ticket.enums.TransactionStatus;
import sapo.intern.mock.carstore.ticket.models.Transaction;
import sapo.intern.mock.carstore.ticket.repositories.TicketRepo;
import sapo.intern.mock.carstore.ticket.repositories.TransactionRepo;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;

import java.text.SimpleDateFormat;
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

    public int getTransactions(Date fromDate, Date toDate) {
        return 0;
    }

    @EventListener(TicketCompleted.class)
    public void handleTicketCompleted(TicketCompleted event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        var ticketId = event.getTicketId();
        var ticket = ticketRepo.getReferenceById(ticketId);
        var newTrans = new Transaction();
        newTrans.setStatus(TransactionStatus.PAID);
        newTrans.setAmount(ticket.getTotalAmount());
        newTrans.setCreatedDate(dateFormat.format(new Date(System.currentTimeMillis())));
        transactionRepo.save(newTrans);
    }
    @EventListener(TicketCanceled.class)
    public void handleTicketCanceled(TicketCanceled event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        var ticketId = event.getTicketId();
        var ticket = ticketRepo.getReferenceById(ticketId);
        var newTrans = new Transaction();
        newTrans.setStatus(TransactionStatus.CANCELED);
        newTrans.setAmount(ticket.getTotalAmount());
        newTrans.setCreatedDate(dateFormat.format(new Date(System.currentTimeMillis())));
        transactionRepo.save(newTrans);
    }
}
