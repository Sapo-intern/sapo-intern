package sapo.intern.mock.carstore.product.services;

import sapo.intern.mock.carstore.product.models.Product;

import java.util.List;

public interface ProductService {
    public List<Product> listAllProducts();
    public Product getProductById(Integer id);
    public Product saveProduct(Product product);
    public Product deleteProduct(Integer id);
    public Product updateProduct(Integer id, Product product);
}
