package sapo.intern.mock.carstore.ticket.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.PayMethod;
import sapo.intern.mock.carstore.ticket.enums.TransactionStatus;

import java.util.Date;

@Table(name = "transactions")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private PayMethod payMethod;
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private TransactionStatus status;
    private Date createdDate;
}
