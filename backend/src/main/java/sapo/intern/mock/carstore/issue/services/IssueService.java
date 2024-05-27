package sapo.intern.mock.carstore.issue.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.issue.models.IssueProduct;
import sapo.intern.mock.carstore.issue.models.Product;
import sapo.intern.mock.carstore.issue.models.RepairService;
import sapo.intern.mock.carstore.issue.repositories.IssueProductRepo;
import sapo.intern.mock.carstore.issue.repositories.IssueRepo;
import sapo.intern.mock.carstore.issue.repositories.ProductRepo;
import sapo.intern.mock.carstore.issue.repositories.RepairServiceRepo;

import java.util.List;

@AllArgsConstructor
@Service
public class IssueService {
    private IssueRepo issueRepo;
    private ProductRepo productRepo;
    private RepairServiceRepo serviceRepo;
    private IssueProductRepo issueProductRepo;
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
}
