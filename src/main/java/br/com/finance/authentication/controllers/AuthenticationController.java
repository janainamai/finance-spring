package br.com.finance.authentication.controllers;

import br.com.finance.authentication.controllers.client.input.LoginInput;
import br.com.finance.authentication.controllers.client.input.RegisterUserInput;
import br.com.finance.authentication.controllers.client.output.LoginOutput;
import br.com.finance.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

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
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterUserInput input) {
        service.register(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
