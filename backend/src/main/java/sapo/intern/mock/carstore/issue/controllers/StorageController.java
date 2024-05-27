package sapo.intern.mock.carstore.issue.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.issue.dtos.ProductQuantity;
import sapo.intern.mock.carstore.issue.dtos.StorageDto;
import sapo.intern.mock.carstore.issue.services.StorageService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/storages")
public class StorageController {

    private StorageService storageService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> requestSaveStorageTransaction(
            @RequestBody StorageDto request
    ) {
        return ResponseEntity.ok(new ApiResponse("1010", storageService.saveStorage(request.getProductId(), request.getQuantity(), request.getType())));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ProductQuantity>>> getTotalQuantityForAllProducts() {
        List<ProductQuantity> totalQuantities = storageService.getTotalQuantityForAllProducts();
        ApiResponse<List<ProductQuantity>> response = new ApiResponse<>("1000", totalQuantities);
        return ResponseEntity.ok(response);
    }
}
