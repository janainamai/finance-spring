package br.com.finance.authentication.controllers;

import br.com.finance.authentication.controllers.client.input.LoginInput;
import br.com.finance.authentication.controllers.client.input.RegisterUserInput;
import br.com.finance.authentication.controllers.client.output.LoginOutput;
import br.com.finance.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationUserController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<LoginOutput> login(@Valid @RequestBody LoginInput input) {
        var token = service.login(input.toDto());

        LoginOutput output = new LoginOutput();
        output.setToken(token);
        return ResponseEntity.ok(output);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserInput input) {
        service.register(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
