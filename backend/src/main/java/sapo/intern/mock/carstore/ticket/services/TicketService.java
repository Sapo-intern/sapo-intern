package sapo.intern.mock.carstore.ticket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.intern.mock.carstore.event.ProductStorage;
import sapo.intern.mock.carstore.event.TicketCompleted;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.issue.enums.StorageType;
import sapo.intern.mock.carstore.issue.helper.IssueProductKey;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.issue.models.IssueProduct;
import sapo.intern.mock.carstore.issue.repositories.IssueRepo;
import sapo.intern.mock.carstore.issue.repositories.ProductRepo;
import sapo.intern.mock.carstore.issue.repositories.RepairServiceRepo;
import sapo.intern.mock.carstore.ticket.dtos.AddIssueRequest;
import sapo.intern.mock.carstore.ticket.dtos.CreateTicketRequest;
import sapo.intern.mock.carstore.ticket.dtos.TicketStatistic;
import sapo.intern.mock.carstore.ticket.enums.TicketStatus;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.models.Ticket;
import sapo.intern.mock.carstore.ticket.repositories.CustomerRepo;
import sapo.intern.mock.carstore.ticket.repositories.TicketRepo;
import sapo.intern.mock.carstore.ticket.repositories.VehicleRepo;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepo ticketRepo;
    private final IssueRepo issueRepo;
    private final CustomerRepo customerRepo;
    private final UserRepo userRepo;
    private final RepairServiceRepo serviceRepo;
    private final ProductRepo productRepo;
    private final CustomerService customerService;
    private final VehicleRepo vehicleRepo;
    private final ApplicationEventPublisher applicationEventPublisher;


    public List<Ticket> getTicketList(Integer page, Integer size) {
        return ticketRepo.findAll(PageRequest.of(page, size)).stream().toList();
    }

    public Issue addIssue(Long ticketId, AddIssueRequest request) {
        var foundTicket = ticketRepo.findById(ticketId).orElseThrow(()-> new NotFoundException("Không tìm thấy ticket!"));
        var foundEmployee = userRepo.findById(request.getEmployeeId().toString()).orElseThrow(()-> new NotFoundException("Không tìm thấy employee!"));
        var foundService = serviceRepo.findById(request.getServiceId()).orElseThrow(()-> new NotFoundException("Không tìm thấy service!"));
        var foundProduct = productRepo.findById(request.getProductId()).orElseThrow(()-> new NotFoundException("Không tìm thấy product!"));
        var newIssue = new Issue();
        newIssue.addIssueProduct(new IssueProduct(new IssueProductKey(), foundProduct, newIssue, request.getQuantity()));
        foundService.addIssue(newIssue);
        foundEmployee.addIssue(newIssue);
        foundTicket.addIssue(newIssue);
        return issueRepo.save(newIssue);
    }

    @Transactional
    public void deleteIssue(Long ticketId, Long issueId) {
        Ticket foundTicket = ticketRepo.findById(ticketId).orElse(null);
        Issue foundIssue = issueRepo.findById(issueId).orElse(null);
        if (foundTicket == null) {
            throw new NotFoundException("Không tồn tại khách hàng " + ticketId);
        } else if (foundIssue == null) {
            throw new NotFoundException("Không tồn tại phương tiện " + issueId);
        }
        List<Issue> ticketIssues = foundTicket.getIssues();
        if (ticketIssues.isEmpty()) {
            throw new NotFoundException("Không tồn tại vấn đề");
        } else if (!foundTicket.getIssues().stream().map(Issue::getId).toList().contains(issueId)) {
            throw new NotFoundException("Không tồn tại vấn đề trong khách hàng " + issueId);
        } else {
            foundTicket.removeIssue(foundIssue);
            ticketRepo.save(foundTicket);
        }
    }

    public Ticket assignCustomer(Long ticketId, Long customerId) {
        Ticket foundTicket = ticketRepo.findById(ticketId).orElse(null);
        Customer foundCustomer = customerRepo.findById(customerId).orElse(null);
        if (foundTicket == null) {
            throw new NotFoundException("Không tồn tại ticket " + ticketId);
        }
        foundTicket.setCustomer(foundCustomer);
        return ticketRepo.save(foundTicket);
    }

    @Transactional
    public Ticket createTicket(CreateTicketRequest request) {
        var foundCustomer = request.getCustomer();
        var foundVehicle = request.getVehicle();
        if (foundCustomer.getId() == null) {
            foundCustomer = customerRepo.save(request.getCustomer());
        }
        if (foundVehicle.getId() == null) {
            foundVehicle = vehicleRepo.save(foundVehicle);
        }

        var newTicket = ticketRepo.save(new Ticket());

        foundCustomer.addTicket(newTicket);
        foundVehicle.addTicket(newTicket);
        if (request.getIssueDtos() != null ) {
            for (var issueDto : request.getIssueDtos()) {

                var foundService = serviceRepo.findById(issueDto.getServiceId()).orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ!"));
                var foundProduct = productRepo.findById(issueDto.getProductId()).orElseThrow(() -> new NotFoundException("Không tìm thấy linh kiện!"));
                var foundEmployee = userRepo.findById(issueDto.getEmployeeId().toString()).orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên!"));
                var newIssue = new Issue();
                var newIssueProduct = new IssueProduct();


                newIssueProduct.setQuantity(issueDto.getQuantity());
                newIssueProduct.setProduct(foundProduct);
                newIssueProduct.setIssue(newIssue);
                newIssue.setUser(foundEmployee);


                foundService.addIssue(newIssue);
                newIssue.addIssueProduct(newIssueProduct);
                newTicket.addIssue(newIssue);
            }
        }
        return ticketRepo.save(newTicket);
    }


    @Transactional
    public void deleteTicket(Long ticketId) {
        ticketRepo.deleteById(ticketId);
    }

    public Ticket updateTicket(Long ticketId, Ticket ticket) {
        Ticket foundTicket = ticketRepo.findById(ticketId).orElse(null);
        if (foundTicket == null) {
            throw new NotFoundException("Không tồn tại ticket " + ticketId);
        }
        foundTicket.setTicket(ticket);
        return ticketRepo.save(foundTicket);
    }

    public Ticket updateTicketComplete(Long ticketId) {
        Ticket foundTicket = ticketRepo.findById(ticketId).orElseThrow(() -> new NotFoundException("Không tồn tại ticket " + ticketId));
        foundTicket.setStatus(TicketStatus.PAID);
        foundTicket.setCompleteDate(new Date(System.currentTimeMillis()));
        var savedTicket = ticketRepo.save(foundTicket);
        applicationEventPublisher.publishEvent(new TicketCompleted(savedTicket.getId()));
        for (var issue : savedTicket.getIssues()) {
            for (var issueProduct : issue.getIssueProducts()) {
                applicationEventPublisher.publishEvent(new ProductStorage(issueProduct.getId().getProductId(), issueProduct.getQuantity(), StorageType.SALE));
            }
        }
        return savedTicket;
    }

    public Ticket getTicketById(Long ticketId) {
        return ticketRepo.findById(ticketId).orElseThrow(() -> new NotFoundException("Không tìm thấy phiếu sửa chữa!"));
    }

    public Ticket updateTicketCancel(Long ticketId) {
        var foundTicket = ticketRepo.findById(ticketId).orElseThrow(() -> new NotFoundException("Không tìm thấy phiếu sửa chữa!"));
        foundTicket.setCompleteDate(null);
        foundTicket.setStatus(TicketStatus.CANCELED);
        for (var issue : foundTicket.getIssues()) {
            for (var issueProduct : issue.getIssueProducts()) {
                applicationEventPublisher.publishEvent(new ProductStorage(issueProduct.getId().getProductId(), issueProduct.getQuantity(), StorageType.CANCELED));
            }
        }
        return ticketRepo.save(foundTicket);
    }

    public List<TicketStatistic> getStatistic() {
        var toDate = new java.util.Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        var fromDate = calendar.getTime();
        return ticketRepo.getStatistic(fromDate, toDate);
    }

    public long countAll() {
        return ticketRepo.countComplete();
    }
}
