package sapo.intern.mock.carstore.issue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.issue.dtos.ProductCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ProductUpdateRequest;
import sapo.intern.mock.carstore.issue.models.Products;
import sapo.intern.mock.carstore.issue.repositories.ProductRepo;
import sapo.intern.mock.carstore.user.models.User;

import java.util.List;

@Service
public class ProductServices {
    @Autowired
    private ProductRepo productRepo;

    public Products getProduct(Long id){
        return productRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA));
    }

    public Products createProduct(ProductCreateRequest request){
        Products products = new Products();

        Products existingName = productRepo.findByName(request.getName());
        if (existingName != null) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        products.setProductCode(request.getProductCode());
        products.setName(request.getName());
        products.setPrice(request.getPrice());
        products.setQuantity(request.getQuantity());
        products.setDescription(request.getDescription());
        products.setUrlImage(request.getUrlImage());

        return productRepo.save(products);

    }

    public Products updateProduct(Long productId, ProductUpdateRequest request){
        Products products = getProduct(productId);

        products.setProductCode(request.getProductCode());
        products.setName(request.getName());
        products.setPrice(request.getPrice());
        products.setDescription(request.getDescription());
        products.setUrlImage(request.getUrlImage());
        products.setQuantity(request.getQuantity());


        return productRepo.save(products);
    }

    public void deleteProduct(Long productId){
        productRepo.deleteById(productId);
    }

    public List<Products> getProductByName(String name) {
        Products exampleProducts = new Products();
        exampleProducts.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Products> example = Example.of(exampleProducts, matcher);

        List<Products> products = productRepo.findAll(example, Sort.by(Sort.Direction.ASC, "name"));

        if(products.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA);
        }

        return products;
    }


    public Page<Products> getAllProductPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAll(pageable);
    }
}
