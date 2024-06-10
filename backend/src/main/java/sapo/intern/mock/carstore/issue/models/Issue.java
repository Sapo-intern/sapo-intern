package sapo.intern.mock.carstore.issue.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.IssueStatus;
import sapo.intern.mock.carstore.ticket.dtos.CreateIssueDto;
import sapo.intern.mock.carstore.ticket.enums.TicketStatus;
import sapo.intern.mock.carstore.ticket.models.Ticket;
import sapo.intern.mock.carstore.user.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Table(name="issues")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Issue {
    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Min(0)
    @Max(100)
    private int progress = 0;
    @Enumerated(EnumType.ORDINAL)
    private IssueStatus status = IssueStatus.PENDING;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issue", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IssueProduct> issueProducts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    private RepairService repairService;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setProgress(int progress) {
        if (progress == 100) this.status = IssueStatus.COMPLETE;
        var complete = true;
        for (Issue issue : ticket.getIssues()) {
            if (issue.getProgress() != 100) {
                complete = false;
            }
        }
        if (complete) {
            ticket.setStatus(TicketStatus.COMPLETE);
        } else {
            ticket.setStatus(TicketStatus.PENDING);
        }
        this.progress = progress;
    }

    public void setIssue(Issue issue) {
        this.description = issue.description;
        this.setProgress(issue.getProgress());
    }

    public double getTotalAmount() {
        if (issueProducts == null || issueProducts.isEmpty()) return 0;
        double totalAmount =0;
        for (int i = 0; i < issueProducts.size(); i++) {
            double unitPrice = issueProducts.get(i).getProduct().getUnitPrice();
            int quantity = issueProducts.get(i).getQuantity();
            totalAmount += unitPrice * quantity;
        }
        return totalAmount + repairService.getPrice();
    }

    public void removeProduct(Product product) {
        for (Iterator<IssueProduct> iterator = issueProducts.iterator(); iterator.hasNext(); ) {
            IssueProduct issueProduct = iterator.next();

            if (issueProduct.getIssue().equals(this) && issueProduct.getProduct().equals(product)) {
                iterator.remove();
                issueProduct.getProduct().getIssueProducts().remove(issueProduct);
                issueProduct.setIssue(null);
                issueProduct.setProduct(null);
            }
        }
    }

    public void addIssueProduct(IssueProduct newIssueProduct) {
        newIssueProduct.setIssue(this);
        issueProducts.add(newIssueProduct);
    }


}
