package sapo.intern.mock.carstore.issue.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.helper.IssueProductKey;
import sapo.intern.mock.carstore.issue.models.Issue;

@AllArgsConstructor
@Getter
@Setter
public class UpdateIssueRequest {
    private Long serviceId;
    private Long employeeId;
    private Long productId;
    private int quantity;
    private IssueProductKey issueProductId;
}
