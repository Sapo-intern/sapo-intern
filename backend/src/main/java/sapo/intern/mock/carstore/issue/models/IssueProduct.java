package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.helper.IssueProductKey;

@Getter
@Setter
@Table
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IssueProduct {
    @EmbeddedId
    IssueProductKey id;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @MapsId("issueId")
    @JoinColumn(name = "issue_id")
    Issue issue;

    int quantity;
}
