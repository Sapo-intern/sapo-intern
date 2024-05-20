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
        Customer foundCustomer = customerRepo.findById(customerId).orElse(null);
        if (foundCustomer == null) {
            throw new NotFoundException("Không tồn tại khách hàng " +  customer.getId().toString());
        }
        foundCustomer.setCustomer(customer);
        return foundCustomer;
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
        foundCustomer.addVehicle(vehicle);
        return vehicleRepo.save(vehicle);
    }

    public Vehicle updateVehicle(Long customerId, Long vehicleId, Vehicle vehicle) {
        Customer foundCustomer = customerRepo.findById(customerId).orElse(null);
        if (foundCustomer == null) {
            throw new NotFoundException("Không tồn tại khách hàng " + customerId);
        }
        Vehicle customerVehicle = foundCustomer.getVehicles()
                .stream().filter(iVehicle -> Objects.equals(iVehicle.getId(), vehicleId))
                .toList().getFirst();
        if (customerVehicle == null){
            throw new NotFoundException("Không tồn tại phương tiện trong khách hàng");
        } else {
            customerVehicle.setVehicle(vehicle);
            return vehicleRepo.save(customerVehicle);
        }
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        if (customerRepo.findById(customerId).orElse(null) == null) {
            throw new NotFoundException("Không tồn tại khách hàng " + customerId);
        }
        customerRepo.deleteById(customerId);
    }

    @Transactional
    public void deleteVehicle(Long customerId, Long vehicleId) {
        Customer foundCustomer = customerRepo.findById(customerId).orElse(null);
        Vehicle foundVehicle = vehicleRepo.findById(vehicleId).orElse(null);
        if (foundCustomer == null) {
            throw new NotFoundException("Không tồn tại khách hàng " + customerId);
        } else if (foundVehicle == null) {
            throw new NotFoundException("Không tồn tại phương tiện " + vehicleId);
        }
        List<Vehicle> customerVehicles = foundCustomer.getVehicles();
        if (customerVehicles.isEmpty()){
            throw new NotFoundException("Khách hàng không có phương tiện");
        } else if (!foundCustomer.getVehicles()
                .stream().map(Vehicle::getId).toList()
                .contains(vehicleId)) {
            throw new NotFoundException("Không tồn tại phương tiện trong khách hàng " + customerId);
        } else {
            foundCustomer.removeVehicle(foundVehicle);
            customerRepo.save(foundCustomer);
        }
    }

    public List<Vehicle> getVehicles(Long customerId) {
        Customer foundCustomer = customerRepo.findById(customerId).orElse(null);
        if (foundCustomer == null) {
            throw new NotFoundException("Không tồn tại khách hàng " + customerId);
        }
        return foundCustomer.getVehicles();
    }
}
