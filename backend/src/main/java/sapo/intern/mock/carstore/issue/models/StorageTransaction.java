package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.StorageType;

import java.sql.Date;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
public class StorageTransaction {
    @Id
    private Long id;
    private Date createdDate = new Date(System.currentTimeMillis());
    private int quantity;
    private StorageType type;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public StorageTransaction(int quantity, StorageType type, Product product) {
        this.quantity = quantity;
        this.type = type;
        this.product = product;
    }
}
