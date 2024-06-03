package sapo.intern.mock.carstore.ticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.models.Vehicle;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CreateTicketRequest {
    private Customer customer;
    private Vehicle vehicle;
    private List<CreateIssueDto> issueDtos;
}
