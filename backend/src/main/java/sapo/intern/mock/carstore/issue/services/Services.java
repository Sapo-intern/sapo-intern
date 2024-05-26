package sapo.intern.mock.carstore.issue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.issue.dtos.ServicesCreateRequest;
import sapo.intern.mock.carstore.issue.dtos.ServicesUpdateRequest;
import sapo.intern.mock.carstore.issue.models.ServiceModal;
import sapo.intern.mock.carstore.issue.repositories.ServicesRepo;

import java.util.List;

@Service
public class Services {
    @Autowired
    private ServicesRepo servicesRepo;

    public ServiceModal getServices(Long id){
        return servicesRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA));
    }

    public ServiceModal createServices(ServicesCreateRequest request){
        ServiceModal services = new ServiceModal();

        ServiceModal existingName = servicesRepo.findByName(request.getName());
        if (existingName != null) {
            throw new AppException(ErrorCode.SERVICES_EXISTED);
        }

        services.setServicesCode(request.getServicesCode());
        services.setName(request.getName());
        services.setPrice(request.getPrice());
        services.setDescription(request.getDescription());

        return servicesRepo.save(services);

    }

    public ServiceModal updateServices(Long servicesId, ServicesUpdateRequest request){
        ServiceModal services = getServices(servicesId);

        services.setServicesCode(request.getServicesCode());
        services.setName(request.getName());
        services.setPrice(request.getPrice());
        services.setDescription(request.getDescription());

        return servicesRepo.save(services);
    }

    public void deleteServices(Long servicesId){
        servicesRepo.deleteById(servicesId);
    }

    public List<ServiceModal> getServicestByName(String name) {
        ServiceModal exampleServices = new ServiceModal();
        exampleServices.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<ServiceModal> example = Example.of(exampleServices, matcher);

        List<ServiceModal> services = servicesRepo.findAll(example, Sort.by(Sort.Direction.ASC, "name"));

        if(services.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA);
        }

        return services;
    }


    public Page<ServiceModal> getAllServicesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return servicesRepo.findAll(pageable);
    }
}
