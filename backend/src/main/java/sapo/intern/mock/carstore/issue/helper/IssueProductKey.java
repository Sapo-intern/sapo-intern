package sapo.intern.mock.carstore.issue.helper;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class IssueProductKey {
    @Column(name = "product_id")
    Long productId;

    @Column(name = "issue_id")
    Long issueId;

}
