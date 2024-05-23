package sapo.intern.mock.carstore.product.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.product.models.Product;
import sapo.intern.mock.carstore.product.repositories.ProductRepo;
import sapo.intern.mock.carstore.product.services.ProductService;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo repository;
    @Override
    public List<Product> listAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return repository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product deleteProduct(Integer id) {
        Product product = getProductById(id);
        repository.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        Product productFromDB = getProductById(id);
        if (product.getProductName() != null) {
            productFromDB.setProductName(product.getProductName());
        }
        if (product.getProductPrice() != 0) {
            productFromDB.setProductPrice(product.getProductPrice());
        }
        if (product.getProductDescription() != null) {
            productFromDB.setProductDescription(product.getProductDescription());
        }
        if(product.getProductQuantity() != 0) {
            productFromDB.setProductQuantity(product.getProductQuantity());
        }
        return saveProduct(productFromDB);
    }
}
