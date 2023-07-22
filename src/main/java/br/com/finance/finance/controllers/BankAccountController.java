package br.com.finance.finance.controllers;

import br.com.finance.finance.controllers.client.input.CreateBankAccountInput;
import br.com.finance.finance.controllers.client.output.BankAccountOutput;
import br.com.finance.finance.services.BankAccountService;
import br.com.finance.finance.services.dto.BankAccountDto;
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
    public ResponseEntity<List<BankAccountOutput>> retrieveAll() {
        List<BankAccountDto> accounts = service.retrieveAll();

        return ResponseEntity.ok(BankAccountOutput.fromDtos(accounts));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateBankAccountInput input) {
        service.create(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
