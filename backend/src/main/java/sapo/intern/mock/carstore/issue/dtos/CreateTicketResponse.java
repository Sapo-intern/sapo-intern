package sapo.intern.mock.carstore.issue.dtos;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Setter
public class CreateTicketResponse {
    private Long ticketId;
    private String description;
    private Date createdDate;
    private Date completeDate;
    private double totalAmount;


}
