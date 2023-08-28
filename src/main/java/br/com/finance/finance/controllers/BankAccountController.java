package br.com.finance.finance.controllers;

import br.com.finance.finance.controllers.client.input.CreateBankAccountInput;
import br.com.finance.finance.controllers.client.input.UpdateBankAccountInput;
import br.com.finance.finance.services.interfaces.BankAccountService;
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
    public ResponseEntity<List<BankAccountDto>> getAll() {
        List<BankAccountDto> accounts = service.getAll();

        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateBankAccountInput input) {
        service.create(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody UpdateBankAccountInput input) {
        service.update(input.toDto());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<BankAccountDto> deactivate(@PathVariable String id) {
         service.deactivate(id);

         return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<BankAccountDto> activate(@PathVariable String id) {
        service.activate(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getById(@PathVariable String id) {
        BankAccountDto bankAccount = service.getDtoById(id);

        return ResponseEntity.ok(bankAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
