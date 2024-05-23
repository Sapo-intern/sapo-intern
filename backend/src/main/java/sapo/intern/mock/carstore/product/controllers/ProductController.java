package sapo.intern.mock.carstore.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.product.models.Product;
import sapo.intern.mock.carstore.product.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.listAllProducts();
        if (products == null) return new ResponseEntity<>(null, HttpStatusCode.valueOf(1040));
        return new ResponseEntity<>(products, HttpStatusCode.valueOf(1001));
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable(value = "id") Integer productId){
        Product product = productService.getProductById(productId);
        if (product == null) return new ResponseEntity<>(null, HttpStatusCode.valueOf(1040));
        return new ResponseEntity<>(product, HttpStatusCode.valueOf(1001));
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product postedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(postedProduct, HttpStatusCode.valueOf(1020));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Integer productId, @RequestBody Product product){
        Product updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.valueOf(1001));
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") Integer productId){
        Product deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.valueOf(1001));
    }
}
