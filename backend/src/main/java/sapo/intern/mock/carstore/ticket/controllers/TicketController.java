package sapo.intern.mock.carstore.ticket.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.issue.models.Product;
import sapo.intern.mock.carstore.ticket.dtos.AddIssueRequest;
import sapo.intern.mock.carstore.ticket.dtos.CreateTicketRequest;
import sapo.intern.mock.carstore.ticket.dtos.UpdateTicketRequest;
import sapo.intern.mock.carstore.ticket.models.Ticket;
import sapo.intern.mock.carstore.ticket.services.TicketService;

import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('COORDINATOR')")
@RequestMapping("/tickets")
public class TicketController {
    private TicketService ticketService;
    @GetMapping("/")
    public ResponseEntity<Page<Ticket>> requestGetTickets(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Ticket> ticketPage = ticketService.getTicketList(page, size);
        return ResponseEntity.ok(ticketPage);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<Ticket>> requestGetTicketDetail(
            @PathVariable("ticketId") Long ticketId
    ) {
        return ResponseEntity.ok(new ApiResponse<>("1000", ticketService.getTicketById(ticketId)));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Ticket>> requestCreateTicket(
            @RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("1020", ticketService.createTicket(request)));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<String>> requestDeleteTicket(
            @PathVariable("ticketId") Long ticketId
    ) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok(new ApiResponse<>("1030", "Xóa thành công!"));
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<Ticket>> requestUpdateTicket(
            @RequestBody UpdateTicketRequest request,
            @PathVariable("ticketId") Long ticketId
    ) {
        return ResponseEntity.ok(new ApiResponse<>("1020", ticketService.updateTicket(ticketId, request.getTicket())));
    }

    @PostMapping("/{ticketId}/issues/")
    public ResponseEntity<ApiResponse<Issue>> requestAddIssue(
            @PathVariable("ticketId") Long ticketId,
            @RequestBody AddIssueRequest request
            ) {
        return ResponseEntity.ok(new ApiResponse<>("1020", ticketService.addIssue(ticketId, request)));
    }

    @DeleteMapping("/{ticketId}/issues/{issueId}")
    public ResponseEntity<ApiResponse<String>> requestRemoveIssue(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("issueId") Long issueId
    ) {
        ticketService.deleteIssue(ticketId, issueId);
        return ResponseEntity.ok(new ApiResponse<>("1030", "Xóa thành công!"));
    }

    @PostMapping("/{ticketId}/customers/{customerId}")
    public ResponseEntity<ApiResponse<Ticket>> requestAssignCustomer(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("issueId") Long customerId
    ) {
        return ResponseEntity.ok(new ApiResponse<>("1020", ticketService.assignCustomer(ticketId, customerId)));
    }

    @PostMapping("/{ticketId}/complete")
    public ResponseEntity<ApiResponse> requestCompleteTicket(
            @PathVariable Long ticketId) {
        return ResponseEntity.ok(new ApiResponse("1020", ticketService.updateTicketComplete(ticketId)));
    }

    @PostMapping("/{ticketId}/cancel")
    public ResponseEntity<ApiResponse> requestCancelTicket(
            @PathVariable Long ticketId) {
        return ResponseEntity.ok(new ApiResponse("1020", ticketService.updateTicketCancel(ticketId)));
    }


    @GetMapping("/statistic")
    public ResponseEntity<ApiResponse> requestGetStatistic() {
        return ResponseEntity.ok(new ApiResponse("1000", ticketService.getStatistic()));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> requestCount() {
        return ResponseEntity.ok(new ApiResponse("1000", ticketService.countAll()));
    }

}
