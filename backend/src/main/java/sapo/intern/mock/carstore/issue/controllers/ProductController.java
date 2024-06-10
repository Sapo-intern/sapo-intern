package sapo.intern.mock.carstore.issue.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.issue.dtos.ProductCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ProductUpdateRequest;
import sapo.intern.mock.carstore.issue.models.Product;
import sapo.intern.mock.carstore.issue.services.ProductServices;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {
    @Autowired
    private ProductServices productServices;
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductCreateRequest request){
        Product products = productServices.createProduct(request);
        ApiResponse<Product> reponse = ApiResponse.<Product>builder()
                .message("Thêm sản phẩm thành công")
                .result(products)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProductsPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Product> productsPage = productServices.getAllProductPaginated(page, size);
        return ResponseEntity.ok(productsPage);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable("productId") Long productId){
        Product products = productServices.getProduct(productId);

        ApiResponse<Product> reponse = ApiResponse.<Product>builder()
                .message("Lấy dữ liệu thành công")
                .result(products)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long productId,@Valid @RequestBody ProductUpdateRequest request){
        Product products = productServices.updateProduct(productId, request);
        ApiResponse<Product> reponse = ApiResponse.<Product>builder()
                .message("Cập nhật dữ liệu thành công")
                .result(products)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long productId){
        productServices.deleteProduct(productId);

        ApiResponse<String> reponse = ApiResponse.<String>builder()
                .message("Xóa thành công")
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/search")
    List<Product> getProductsByName(@RequestParam String name) {
        return productServices.getProductByName(name);
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> requestCount() {
        ApiResponse<Long> reponse = ApiResponse.<Long>builder()
                .message("Lấy dữ liệu thành công")
                .result(productServices.countAll())
                .build();
        return ResponseEntity.ok(reponse);
    }
}
