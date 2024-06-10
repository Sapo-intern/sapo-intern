package sapo.intern.mock.carstore.ticket.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.IssueStatus;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.ticket.enums.TicketStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tickets")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Date createdDate = new Date(System.currentTimeMillis());
    private Date completeDate;
    private double totalAmount;
    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();
    @Enumerated(EnumType.ORDINAL)
    private TicketStatus status = TicketStatus.PENDING;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;


    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setTicket(this);
    }

    public void setStatus(TicketStatus status) {
        if (this.status == TicketStatus.CANCELED || this.status == TicketStatus.PAID) {
            return;
        }
        if (status == TicketStatus.PAID) {
            this.completeDate = new Date(System.currentTimeMillis());
        }
        this.status = status;
    }


    public TicketStatus getStatus() {
        if (status == TicketStatus.PAID || status == TicketStatus.CANCELED) return status;
        var complete = true;
        for (var issue : issues) {
            if (issue.getStatus() != IssueStatus.COMPLETE) {
                complete = false;
                break;
            }
        }
        if (complete) {
            return TicketStatus.COMPLETE;
        } else {
            return TicketStatus.PENDING;
        }
    }




    public double getTotalAmount() {
        double totalAmount = 0;
        for (int i = 0; i < issues.size(); i++) {
            totalAmount += issues.get(i).getTotalAmount();
        }
        return totalAmount;
    }

    public void setTicket(Ticket ticket) {
        setDescription(ticket.getDescription());
        setCreatedDate(ticket.getCreatedDate());
        setCompleteDate(ticket.getCompleteDate());
        setStatus(ticket.getStatus());
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue);
        issue.setTicket(null);
    }
}
