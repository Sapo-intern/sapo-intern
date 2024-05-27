package sapo.intern.mock.carstore.ticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.ticket.models.Ticket;

@AllArgsConstructor
@Getter
@Setter
public class CreateTicketRequest {
    private Ticket ticket;
}
