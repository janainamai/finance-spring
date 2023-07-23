package br.com.finance.finance.controllers;

import br.com.finance.finance.controllers.client.input.CreateBankAccountInput;
import br.com.finance.finance.controllers.client.output.BankAccountOutput;
import br.com.finance.finance.services.BankAccountService;
import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.utils.FinanceUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/bank")
public class BankAccountController {

    @Autowired
    private BankAccountService service;

    @GetMapping
    public ResponseEntity<List<BankAccountOutput>> getAll() {
        List<BankAccountDto> accounts = service.getAll();

        return ResponseEntity.ok(BankAccountOutput.fromDtos(accounts));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateBankAccountInput input) {
        service.create(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getById(@PathVariable String id) {
        BankAccountDto bankAccount = service.getById(FinanceUtils.stringToUUID(id));

        return ResponseEntity.ok(bankAccount);
    }

}
