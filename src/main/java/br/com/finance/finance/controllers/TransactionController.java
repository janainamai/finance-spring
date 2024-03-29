package br.com.finance.finance.controllers;

import br.com.finance.finance.controllers.client.input.CreateTransactionInput;
import br.com.finance.finance.controllers.client.input.FilterTransactionInput;
import br.com.finance.finance.services.dto.TransactionDto;
import br.com.finance.finance.services.interfaces.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable String id) {
        TransactionDto transaction = service.getById(id);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        List<TransactionDto> transactions = service.getAll();

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<TransactionDto>> getAllByFilters(@Valid @RequestBody FilterTransactionInput input) {
        Page<TransactionDto> page = service.getAllByFilters(input.toDto());

        return ResponseEntity.ok(page);
    }

    @GetMapping("/bankAccount/{bankAccountId}")
    public ResponseEntity<List<TransactionDto>> getAllByBankAccountId(@PathVariable String bankAccountId) {
        List<TransactionDto> transactionDtos = service.getAllByBankAccountId(bankAccountId);

        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CreateTransactionInput input) {
        service.create(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
