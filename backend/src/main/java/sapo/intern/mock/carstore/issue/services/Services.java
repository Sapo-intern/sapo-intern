package sapo.intern.mock.carstore.issue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.issue.dtos.ServicesCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ServicesUpdateRequest;
import sapo.intern.mock.carstore.issue.models.RepairService;
import sapo.intern.mock.carstore.issue.repositories.RepairServiceRepo;

import java.util.List;

@Service
public class Services {
    @Autowired
    private RepairServiceRepo serviceRepo;

    public RepairService getServices(Long id){
        return serviceRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA));
    }

    public RepairService createServices(ServicesCreateRequest request){
        RepairService services = new RepairService();

        RepairService existingName = serviceRepo.findByName(request.getName());
        if (existingName != null) {
            throw new AppException(ErrorCode.SERVICES_EXISTED);
        }

        services.setServicesCode(request.getServicesCode());
        services.setName(request.getName());
        services.setPrice(request.getPrice());
        services.setDescription(request.getDescription());

        return serviceRepo.save(services);

    }

    public RepairService updateServices(Long servicesId, ServicesUpdateRequest request){
        RepairService services = getServices(servicesId);

        services.setServicesCode(   request.getServicesCode());
        services.setName(request.getName());
        services.setPrice(request.getPrice());
        services.setDescription(request.getDescription());

        return serviceRepo.save(services);
    }

    public void deleteServices(Long servicesId){
        serviceRepo.deleteById(servicesId);
    }

    public List<RepairService> getServicestByName(String name) {
        RepairService exampleServices = new RepairService();
        exampleServices.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<RepairService> example = Example.of(exampleServices, matcher);

        List<RepairService> services = serviceRepo.findAll(example, Sort.by(Sort.Direction.ASC, "name"));

        if(services.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA);
        }

        return services;
    }


    public Page<RepairService> getAllServicesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return serviceRepo.findAll(pageable);
    }

    public Long countAll() {
        return serviceRepo.count();
    }
}
