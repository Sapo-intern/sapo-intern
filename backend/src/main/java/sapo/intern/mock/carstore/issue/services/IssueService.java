package sapo.intern.mock.carstore.issue.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.issue.dtos.UpdateIssueRequest;
import sapo.intern.mock.carstore.issue.helper.IssueProductKey;
import sapo.intern.mock.carstore.issue.models.*;
import sapo.intern.mock.carstore.issue.repositories.*;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.util.List;

@AllArgsConstructor
@Service
public class IssueService {
    private IssueRepo issueRepo;
    private ProductRepo productRepo;
    private RepairServiceRepo serviceRepo;
    private IssueProductRepo issueProductRepo;
    private UserRepo employeeRepo;

    @Transactional
    public void deleteProduct(Long issueId, Long productId) {
        Issue foundIssue = issueRepo.findById(issueId).orElseThrow(() -> new NotFoundException("Không tồn tại " + issueId));
        Product foundProduct = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Không tồn tại " + productId));

        boolean productExistsInIssue = foundIssue.getIssueProducts().stream()
                .anyMatch(issueProduct -> issueProduct.getProduct().getId().equals(productId));

        if (!productExistsInIssue) {
            throw new NotFoundException("Không tồn tại linh kiện trong Vấn đề " + productId);
        }

        foundIssue.removeProduct(foundProduct);
        issueRepo.save(foundIssue);
    }
    @Transactional
    public void deleteService(Long issueId, Long serviceId) {
        Issue foundIssue = issueRepo.findById(issueId).orElse(null);
        RepairService foundService = serviceRepo.findById(serviceId).orElse(null);
        if (foundIssue == null) {
            throw new NotFoundException("Không tồn tại " + issueId);
        } else if (foundService == null) {
            throw new NotFoundException("Không tồn tại " + serviceId);
        }
        RepairService issueService = foundIssue.getRepairService();
        if (!issueService.equals(foundService)) {
            throw new NotFoundException("Không tồn tại linh kiện trong Vấn đề " + serviceId);
        } else {
            foundIssue.setRepairService(null);
            issueRepo.save(foundIssue);
        }
    }


    public Issue updateIssue(Long issueId, UpdateIssueRequest request) {
        var foundIssue = issueRepo.findById(issueId).orElseThrow(()-> new NotFoundException("Không tìm thấy issue!"));
        var foundEmployee = employeeRepo.findById(request.getEmployeeId().toString()).orElseThrow(()-> new NotFoundException("Không tìm thấy employee!"));
        var foundService = serviceRepo.findById(request.getServiceId()).orElseThrow(()-> new NotFoundException("Không tìm thấy service!"));
        var foundProduct = productRepo.findById(request.getProductId()).orElseThrow(()-> new NotFoundException("Không tìm thấy product!"));

        IssueProductKey issueProductKey = request.getIssueProductId();

        // Find the existing IssueProduct
        IssueProduct foundIssueProduct = issueProductRepo.findById(issueProductKey)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy issue product!"));

        foundIssueProduct.setIssue(foundIssue);
        foundIssueProduct.setProduct(foundProduct);
        foundIssueProduct.setQuantity(request.getQuantity());

        return issueRepo.save(foundIssue);
    }

    public RepairService addService(Long issueId, Long serviceId) {
        Issue foundIssue = issueRepo.findById(issueId).orElse(null);
        RepairService foundService = serviceRepo.findById(serviceId).orElse(null);
        if (foundIssue == null) {
            throw new NotFoundException("Không tồn tại " + issueId);
        } else if (foundService == null) {
            throw new NotFoundException("Không tồn tại " + serviceId);
        }

        foundIssue.setRepairService(foundService);
        return issueRepo.save(foundIssue).getRepairService();
    }

    public Product addProduct(Long issueId, Long productId, Integer quantity) {
        Issue foundIssue = issueRepo.findById(issueId).orElse(null);
        Product foundProduct = productRepo.findById(productId).orElse(null);
        if (foundIssue == null) {
            throw new NotFoundException("Không tồn tại " + issueId);
        } else if (foundProduct == null) {
            throw new NotFoundException("Không tồn tại " + productId);
        }
        IssueProduct newIssueProduct = new IssueProduct();
        newIssueProduct.setIssue(foundIssue);
        newIssueProduct.setProduct(foundProduct);
        newIssueProduct.setQuantity(quantity);

        foundProduct.getIssueProducts().add(newIssueProduct);
        foundIssue.getIssueProducts().add(newIssueProduct);

        issueRepo.save(foundIssue);
        productRepo.save(foundProduct);
        issueProductRepo.save(newIssueProduct);

        return foundProduct;
    }

    public Issue assignEmployee(Long issueId, Long employeeId) {
        Issue foundIssue = issueRepo.findById(issueId).orElseThrow(()->new NotFoundException("Không tìm thấy vấn đề!"));
        User foundEmployee = employeeRepo.findById(employeeId.toString()).orElseThrow(()->new NotFoundException("Không tìm thấy nhân viên!"));
        foundIssue.setUser(foundEmployee);
        foundEmployee.addIssue(foundIssue);
        return issueRepo.save(foundIssue);
    }

    public Issue removeEmployee(Long issueId, Long employeeId) {
        Issue foundIssue = issueRepo.findById(issueId).orElseThrow(()->new NotFoundException("Không tìm thấy vấn đề!"));
        User foundEmployee = employeeRepo.findById(employeeId.toString()).orElseThrow(()->new NotFoundException("Không tìm thấy nhân viên!"));
        foundEmployee.removeIssue(foundIssue);
        return issueRepo.save(foundIssue);
    }

    public List<Issue> getIssuesByEmployeeId(Long employeeId) {
        return issueRepo.findAll().stream().filter(issue -> issue.getUser() != null && issue.getUser().getId().equals(employeeId)).toList();
    }

    public Issue updateIssueProgress(Long issueId, int progress) {
        Issue foundIssue = issueRepo.findById(issueId).orElseThrow(()->new NotFoundException("Không tìm thấy vấn đề!"));
        foundIssue.setProgress(progress);
        return issueRepo.save(foundIssue);
    }

    public Issue getIssuesBy(Long issueId) {
        return issueRepo.findById(issueId).orElseThrow(()->new NotFoundException("Không tìm thấy vấn đề!"));
    }
}
