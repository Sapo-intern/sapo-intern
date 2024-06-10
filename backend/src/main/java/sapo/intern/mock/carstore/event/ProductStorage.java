package sapo.intern.mock.carstore.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.StorageType;

@Getter
@Setter
@AllArgsConstructor
public class ProductStorage {
    private Long productId;
    private Integer quantity;
    private StorageType type;
}
