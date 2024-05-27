package sapo.intern.mock.carstore.issue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.issue.dtos.ProductCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ProductUpdateRequest;
import sapo.intern.mock.carstore.issue.models.Product;
import sapo.intern.mock.carstore.issue.repositories.ProductRepo;

import java.util.List;

@Service
public class ProductServices {
    @Autowired
    private ProductRepo productRepo;

    public Product getProduct(Long id){
        return productRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA));
    }

    public Product createProduct(ProductCreateRequest request){
        Product products = new Product();

        Product existingName = productRepo.findByName(request.getName());
        if (existingName != null) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        products.setProductCode(request.getProductCode());
        products.setName(request.getName());
        products.setUnitPrice(request.getPrice());
        products.setDescription(request.getDescription());
        products.setUrlImage(request.getUrlImage());

        return productRepo.save(products);

    }

    public Product updateProduct(Long productId, ProductUpdateRequest request){
        Product products = getProduct(productId);

        products.setProductCode(request.getProductCode());
        products.setName(request.getName());
        products.setUnitPrice(request.getPrice());
        products.setDescription(request.getDescription());
        products.setUrlImage(request.getUrlImage());


        return productRepo.save(products);
    }

    public void deleteProduct(Long productId){
        productRepo.deleteById(productId);
    }

    public List<Product> getProductByName(String name) {
        Product exampleProducts = new Product();
        exampleProducts.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Product> example = Example.of(exampleProducts, matcher);

        List<Product> products = productRepo.findAll(example, Sort.by(Sort.Direction.ASC, "name"));

        if(products.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA);
        }

        return products;
    }


    public Page<Product> getAllProductPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAll(pageable);
    }
}
