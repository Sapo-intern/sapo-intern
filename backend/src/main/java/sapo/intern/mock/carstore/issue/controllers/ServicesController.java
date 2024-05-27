package sapo.intern.mock.carstore.issue.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.issue.dtos.ServicesCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ServicesUpdateRequest;
import sapo.intern.mock.carstore.issue.models.ServiceModal;
import sapo.intern.mock.carstore.issue.services.Services;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/services")
@Validated
public class ServicesController {
    @Autowired
    private Services services;
    @PostMapping
    public ResponseEntity<ApiResponse<ServiceModal>> createServices(@Valid @RequestBody ServicesCreateRequest request){
        ServiceModal servicesController = services.createServices(request);
        ApiResponse<ServiceModal> reponse = ApiResponse.<ServiceModal>builder()
                .message("Thêm dịch vụ thành công")
                .result(servicesController)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<ServiceModal>> getAllServicesPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<ServiceModal> servicesPage = services.getAllServicesPaginated(page, size);
        return ResponseEntity.ok(servicesPage);
    }

    @GetMapping("/{servicesId}")
    public ResponseEntity<ApiResponse<ServiceModal>> getServices(@PathVariable("servicesId") Long servicesId){
        ServiceModal servicesController = services.getServices(servicesId);

        ApiResponse<ServiceModal> reponse = ApiResponse.<ServiceModal>builder()
                .message("Lấy dữ liệu thành công")
                .result(servicesController)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @PatchMapping("/{servicesId}")
    public ResponseEntity<ApiResponse<ServiceModal>> updateServices(@PathVariable Long servicesId,@Valid @RequestBody ServicesUpdateRequest request){
        ServiceModal servicesController = services.updateServices(servicesId, request);
        ApiResponse<ServiceModal> reponse = ApiResponse.<ServiceModal>builder()
                .message("Cập nhật dữ liệu thành công")
                .result(servicesController)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @DeleteMapping("/{servicesId}")
    public ResponseEntity<ApiResponse<String>> deleteServices(@PathVariable Long servicesId){
        services.deleteServices(servicesId);

        ApiResponse<String> reponse = ApiResponse.<String>builder()
                .message("Xóa thành công")
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/search")
    List<ServiceModal> getServicesByName(@RequestParam String name) {
        return services.getServicestByName(name);
    }

}
