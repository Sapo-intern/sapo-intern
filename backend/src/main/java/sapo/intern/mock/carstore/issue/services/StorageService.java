package sapo.intern.mock.carstore.issue.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.issue.dtos.ProductQuantity;
import sapo.intern.mock.carstore.issue.enums.StorageType;
import sapo.intern.mock.carstore.issue.models.Product;
import sapo.intern.mock.carstore.issue.models.StorageTransaction;
import sapo.intern.mock.carstore.issue.repositories.ProductRepo;
import sapo.intern.mock.carstore.issue.repositories.StorageRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class StorageService {
    private StorageRepo storageRepo;
    private ProductRepo productRepo;

    public StorageTransaction saveStorage(Long productId, Long quantity, StorageType type) {
        Product foundProduct =  productRepo.findById(productId).orElseThrow(()->new NotFoundException(" Không tìm thấy sản phẩm!"));
        return storageRepo.save(new StorageTransaction(Math.toIntExact(quantity), type, foundProduct));
    }

    public List<ProductQuantity> getTotalQuantityForAllProducts() {
        return storageRepo.findTotalQuantityForAllProducts();
    }
}
