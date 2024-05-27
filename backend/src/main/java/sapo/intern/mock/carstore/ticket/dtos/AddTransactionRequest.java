package sapo.intern.mock.carstore.ticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.ticket.models.Transaction;

@AllArgsConstructor
@Getter
@Setter
public class AddTransactionRequest {
    private Transaction transaction;
}
