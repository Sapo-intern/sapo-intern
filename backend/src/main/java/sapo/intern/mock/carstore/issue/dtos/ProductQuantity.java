package sapo.intern.mock.carstore.issue.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductQuantity {
    private Long productId;
    private Long totalQuantity;
    private String name;
    private String productCode;
    private Double unitPrice;
}
