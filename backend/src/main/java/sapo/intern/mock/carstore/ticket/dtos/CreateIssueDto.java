package sapo.intern.mock.carstore.ticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateIssueDto {
    private Long employeeId;
    private Long serviceId;
    private Long productId;
    private Integer quantity;
    private String description;
}
