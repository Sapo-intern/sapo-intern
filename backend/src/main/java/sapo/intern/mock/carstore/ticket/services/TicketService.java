package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.issue.repositories.IssueRepo;
import sapo.intern.mock.carstore.ticket.models.Customer;
import sapo.intern.mock.carstore.ticket.models.Ticket;
import sapo.intern.mock.carstore.ticket.models.Vehicle;
import sapo.intern.mock.carstore.ticket.repositories.CustomerRepo;
import sapo.intern.mock.carstore.ticket.repositories.TicketRepo;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class TicketService {
    private TicketRepo ticketRepo;
    private IssueRepo issueRepo;
    private CustomerRepo customerRepo;
    public List<Ticket> getTicketList(Integer page, Integer size) {
        return ticketRepo.findAll(PageRequest.of(page, size)).stream().toList();
    }

    public Ticket addIssue(Long ticketId, Issue issue) {
        Ticket foundTicket = ticketRepo.findById(ticketId).orElse(null);
        if (foundTicket == null) throw new NotFoundException("Không tìm thấy ticket!");
        foundTicket.addIssue(issue);
        return ticketRepo.save(foundTicket);
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
        if (ticketIssues.isEmpty()){
            throw new NotFoundException("Không tồn tại vấn đề");
        } else if (!foundTicket.getIssues()
                .stream().map(Issue::getId).toList()
                .contains(issueId)) {
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

    public Ticket createTicket(Ticket ticket) {
        return ticketRepo.save(ticket);
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
}
