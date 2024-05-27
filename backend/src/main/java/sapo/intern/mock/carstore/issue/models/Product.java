package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.ticket.models.Ticket;

import java.util.List;

@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Product {
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    /*Mnh giá theo .000 vnđ */
    private double unitPrice;
    @OneToMany(mappedBy = "product")
    private List<StorageTransaction> transactions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<IssueProduct> issueProducts;
}
