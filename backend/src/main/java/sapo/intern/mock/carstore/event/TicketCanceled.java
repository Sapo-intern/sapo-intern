package sapo.intern.mock.carstore.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketCanceled {
    private Long ticketId;
}
