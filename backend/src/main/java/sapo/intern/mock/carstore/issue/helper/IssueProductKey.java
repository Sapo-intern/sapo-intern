package sapo.intern.mock.carstore.issue.helper;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueProductKey {
    @Column(name = "product_id")
    Long productId;

    @Column(name = "issue_id")
    Long issueId;

}
