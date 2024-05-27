package sapo.intern.mock.carstore.issue.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.issue.dtos.ServicesCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ServicesUpdateRequest;
import sapo.intern.mock.carstore.issue.models.RepairService;
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
    public ResponseEntity<ApiResponse<RepairService>> createServices(@Valid @RequestBody ServicesCreateRequest request){
        RepairService servicesController = services.createServices(request);
        ApiResponse<RepairService> reponse = ApiResponse.<RepairService>builder()
                .message("Thêm dịch vụ thành công")
                .result(servicesController)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<RepairService>> getAllServicesPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RepairService> servicesPage = services.getAllServicesPaginated(page, size);
        return ResponseEntity.ok(servicesPage);
    }

    @GetMapping("/{servicesId}")
    public ResponseEntity<ApiResponse<RepairService>> getServices(@PathVariable("servicesId") Long servicesId){
        RepairService servicesController = services.getServices(servicesId);

        ApiResponse<RepairService> reponse = ApiResponse.<RepairService>builder()
                .message("Lấy dữ liệu thành công")
                .result(servicesController)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @PatchMapping("/{servicesId}")
    public ResponseEntity<ApiResponse<RepairService>> updateServices(@PathVariable Long servicesId,@Valid @RequestBody ServicesUpdateRequest request){
        RepairService servicesController = services.updateServices(servicesId, request);
        ApiResponse<RepairService> reponse = ApiResponse.<RepairService>builder()
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
    List<RepairService> getServicesByName(@RequestParam String name) {
        return services.getServicestByName(name);
    }

}
