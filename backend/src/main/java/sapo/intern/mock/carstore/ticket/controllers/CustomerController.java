package sapo.intern.mock.carstore.ticket.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.ticket.dtos.CreateCustomerRequest;
import sapo.intern.mock.carstore.ticket.dtos.CreateVehicleRequest;
import sapo.intern.mock.carstore.ticket.dtos.UpdateCustomerRequest;
import sapo.intern.mock.carstore.ticket.dtos.UpdateVehicleRequest;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.models.Vehicle;
import sapo.intern.mock.carstore.ticket.services.CustomerService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Customer>>> requestGetCustomerList(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return ResponseEntity.ok(new ApiResponse<>("1000", customerService.getCustomerList(page, size)));
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
