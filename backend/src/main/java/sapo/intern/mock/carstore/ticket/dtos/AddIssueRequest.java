package sapo.intern.mock.carstore.ticket.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.models.Issue;

@NoArgsConstructor
@Getter
@Setter
public class AddIssueRequest {
    private Issue issue;
}
