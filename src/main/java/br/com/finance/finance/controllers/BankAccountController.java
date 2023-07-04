package br.com.finance.finance.controllers;

import br.com.finance.finance.controllers.client.output.BankAccountOutput;
import br.com.finance.finance.services.BankAccountService;
import br.com.finance.finance.services.dto.BankAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/bank")
public class BankAccountController {

    @Autowired
    private BankAccountService service;

    @GetMapping
    public ResponseEntity<List<BankAccountOutput>> retrieveAll() {
        List<BankAccountDto> accounts = service.retrieveAll();

        return ResponseEntity.ok(BankAccountOutput.fromDtos(accounts));
    }

}
