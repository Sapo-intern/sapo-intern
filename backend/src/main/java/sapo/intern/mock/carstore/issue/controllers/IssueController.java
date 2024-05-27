package sapo.intern.mock.carstore.issue.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.issue.services.IssueService;

@AllArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {
    private IssueService issueService;
    @DeleteMapping("/{issueId}/products/{productId}")
    public ResponseEntity<ApiResponse> requestDeleteProduct(
            @PathVariable("issueId") Long issueId,
            @PathVariable("productId") Long productId
            ) {
        issueService.deleteProduct(issueId, productId);
        return ResponseEntity.ok(new ApiResponse<>("1030", "Delete success!"));
    }

    @DeleteMapping("/{issueId}/services/{serviceId}")
    public ResponseEntity<ApiResponse> requestDeleteService(
            @PathVariable("issueId") Long issueId,
            @PathVariable("serviceId") Long serviceId
            ) {
        issueService.deleteService(issueId, serviceId);
        return ResponseEntity.ok(new ApiResponse<>("1030", "Delete success!"));
    }

    @PostMapping("/{issueId}/services/{serviceId}")
    public ResponseEntity<ApiResponse> requestAddService(
            @PathVariable("issueId") Long issueId,
            @PathVariable("serviceId") Long serviceId
    ) {
        return ResponseEntity.ok(new ApiResponse<>("1010", issueService.addService(issueId, serviceId)));
    }

    @PostMapping("/{issueId}/products/{productId}")
    public ResponseEntity<ApiResponse> requestAddProduct(
            @PathVariable("issueId") Long issueId,
            @PathVariable("productId") Long productId,
            @RequestParam("quantity") Integer quantity
    ) {
        return ResponseEntity.ok(new ApiResponse<>("1010", issueService.addProduct(issueId, productId, quantity)));
    }

}
