package sapo.intern.mock.carstore.ticket.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.ticket.dtos.AddTransactionRequest;
import sapo.intern.mock.carstore.ticket.services.TransactionService;

import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionService transactionService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse> requestSaveTransaction(@RequestBody AddTransactionRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("1010", transactionService.saveTransaction(request.getTransaction())));
    }

    @GetMapping("/get-amounts")
    public ResponseEntity<ApiResponse> requestGetTransactionsAmount(
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        return ResponseEntity.ok(new ApiResponse<>("1000", transactionService.getTransactionsAmount(fromDate, toDate)));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> requestGetTransactions(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return ResponseEntity.ok(new ApiResponse<>("1000", transactionService.getTransactions(page, size)));
    }

    @GetMapping("/statistic")
    public ResponseEntity<ApiResponse> requestGetStatistic() {
        return ResponseEntity.ok(new ApiResponse("1000", transactionService.getStatistic()));
    }

}
