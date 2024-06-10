package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.models.Vehicle;
import sapo.intern.mock.carstore.ticket.repositories.CustomerRepo;
import sapo.intern.mock.carstore.ticket.repositories.VehicleRepo;

import java.util.List;
import java.util.Objects;

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

    public List<Customer> getCustomerList(Integer page, Integer size) {
        List<Customer> foundCustomers = customerRepo.findAll(PageRequest.of(page, size)).stream().toList();
        if (foundCustomers.isEmpty()) {
            throw new NotFoundException("Không tồn tại khách hàng");
        }
        return foundCustomers;
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
