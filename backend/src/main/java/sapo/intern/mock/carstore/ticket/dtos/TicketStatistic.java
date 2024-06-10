package sapo.intern.mock.carstore.ticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TicketStatistic {
    private Long quantity;
    private Date date;
}
