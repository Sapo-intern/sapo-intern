package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import sapo.intern.mock.carstore.ticket.models.Ticket;

import java.util.List;

@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private String name;
    private String urlImage;
    private String description;
    /*Mnh giá theo .000 vnđ */
    private Double unitPrice;
    @OneToMany(mappedBy = "product")
    private List<StorageTransaction> transactions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<IssueProduct> issueProducts;
}
