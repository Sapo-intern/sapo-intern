package sapo.intern.mock.carstore.ticket.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.ticket.dtos.CreateCustomerRequest;
import sapo.intern.mock.carstore.ticket.dtos.UpdateCustomerRequest;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.services.CustomerService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomerPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Customer> customersPage = customerService.getCustomerList(page, size);
        return ResponseEntity.ok(customersPage);
    }

    @GetMapping("/search")
    List<Customer> getCustomerByName(@RequestParam String keyword) {
        return customerService.getUserByKeyword(keyword);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> requestGetCustomersDetail(@PathVariable Long customerId) {
        return ResponseEntity.ok(new ApiResponse("1000", customerService.getCustomerDetail(customerId)));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Customer>> requestCreateCustomer(
            @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(new ApiResponse("1010", customerService.createCustomer(request.getCustomer())));
    }

    @PutMapping("/{customerId}/")
    public ResponseEntity<ApiResponse<Customer>> requestUpdateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("1020", customerService.updateCustomer(customerId, request.getCustomer())));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> requestCount() {
        return ResponseEntity.ok(new ApiResponse("1000", customerService.countAll()));
    }



    /**/
    /*Exception handler*/
    /**/

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ApiResponse> resolveException(RuntimeException e) {
        return ResponseEntity.status(400).body(new ApiResponse("1050", e.toString()));
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ApiResponse> resolveNotFoundException(NotFoundException e) {
        return ResponseEntity.status(404).body(new ApiResponse("1040", e.getErrorMessage()));
    }



}
