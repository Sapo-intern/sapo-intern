package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.models.Vehicle;
import sapo.intern.mock.carstore.ticket.repositories.CustomerRepo;
import sapo.intern.mock.carstore.ticket.repositories.VehicleRepo;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {
    private CustomerRepo customerRepo;
    private VehicleRepo vehicleRepo;
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer customer) {
        Customer foundCustomer = customerRepo.findById(customerId).orElseThrow(()->new NotFoundException("Không tồn tại khách hàng " +  customer.getId().toString()));
        foundCustomer.setCustomer(customer);
        return customerRepo.save(foundCustomer);
    }

    public Page<Customer> getCustomerList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepo.findAll(pageable);
    }

    public List<Customer> getUserByKeyword(String keyword) {
        List<Customer> customer = customerRepo.findByKeywordContainingIgnoreCase(keyword);

        if(customer.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA);
        }

        return customer;
    }


    public Vehicle createVehicle(Long customerId, Vehicle vehicle) {
        Customer foundCustomer = customerRepo.findById(customerId).orElse(null);
        if (foundCustomer == null) {
            throw new NotFoundException("Không tồn tại khách hàng " + customerId);
        }
        return vehicleRepo.save(vehicle);
    }

    public Customer getCustomerDetail(Long customerId) {
        return customerRepo.findById(customerId).orElseThrow(()-> new NotFoundException("Không tồn tại khách hàng " + customerId));
    }

    public long countAll() {
        return customerRepo.count();
    }
}
