package sapo.intern.mock.carstore.issue.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import sapo.intern.mock.carstore.issue.enums.StorageType;
import sapo.intern.mock.carstore.ticket.models.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<StorageTransaction> transactions = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IssueProduct> issueProducts;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public int getQuantity() {
        if (transactions == null) {
            return 0;
        }
        var sumQuantity = 0;
        for (var trans : transactions) {
            if (trans.getType() == StorageType.IMPORT) {
                sumQuantity += trans.getQuantity();
            } else if (trans.getType() == StorageType.SALE) {
                sumQuantity -= trans.getQuantity();
            } else if (trans.getType() == StorageType.CANCELED) {
                sumQuantity += trans.getQuantity();
            }
        }
        return sumQuantity;
    }
}
