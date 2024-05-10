package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.IssueStatus;
import sapo.intern.mock.carstore.ticket.models.Ticket;

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
    private int progress;
    @Enumerated(EnumType.ORDINAL)
    private IssueStatus status;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    private RepairService repairService;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    public void setProgress(int progress) {
        if (progress == 100) this.status = IssueStatus.COMPLETE;
        this.progress = progress;
    }
}
