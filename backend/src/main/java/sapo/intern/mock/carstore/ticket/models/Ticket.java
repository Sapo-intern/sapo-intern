package sapo.intern.mock.carstore.ticket.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.ticket.enums.TicketStatus;

import java.sql.Date;
import java.util.List;

@Table(name = "tickets")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Date createdDate;
    private Date completeDate;
    /**/
    private int totalAmount;
    @OneToMany(mappedBy = "ticket")
    private List<Issue> issues;
    @Enumerated(EnumType.ORDINAL)
    private TicketStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
}
