package sapo.intern.mock.carstore.issue.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.enums.StorageType;

@AllArgsConstructor
@Getter
@Setter
public class StorageDto {
    private Long productId;
    private Long quantity;
    private StorageType type;
}
