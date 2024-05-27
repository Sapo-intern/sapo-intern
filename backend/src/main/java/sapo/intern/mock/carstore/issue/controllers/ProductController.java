package sapo.intern.mock.carstore.issue.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.issue.dtos.ProductCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ProductUpdateRequest;
import sapo.intern.mock.carstore.issue.models.Products;
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
    public ResponseEntity<ApiResponse<Products>> createProduct(@Valid @RequestBody ProductCreateRequest request){
        Products products = productServices.createProduct(request);
        ApiResponse<Products> reponse = ApiResponse.<Products>builder()
                .message("Thêm sản phẩm thành công")
                .result(products)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<Products>> getAllProductsPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Products> productsPage = productServices.getAllProductPaginated(page, size);
        return ResponseEntity.ok(productsPage);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Products>> getProduct(@PathVariable("productId") Long productId){
        Products products = productServices.getProduct(productId);

        ApiResponse<Products> reponse = ApiResponse.<Products>builder()
                .message("Lấy dữ liệu thành công")
                .result(products)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<Products>> updateProduct(@PathVariable Long productId,@Valid @RequestBody ProductUpdateRequest request){
        Products products = productServices.updateProduct(productId, request);
        ApiResponse<Products> reponse = ApiResponse.<Products>builder()
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
    List<Products> getProductsByName(@RequestParam String name) {
        return productServices.getProductByName(name);
    }

}
