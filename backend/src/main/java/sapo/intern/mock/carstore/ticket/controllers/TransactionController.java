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

    @GetMapping("/")
    public ResponseEntity<ApiResponse> requestGetTransactions(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        return ResponseEntity.ok(new ApiResponse<>("1000", transactionService.getTransactions(fromDate, toDate)));
    }

}
