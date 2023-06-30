package br.com.finance.authentication.controllers;

import br.com.finance.authentication.domain.dto.AuthenticationDto;
import br.com.finance.authentication.domain.dto.RegisterUserDto;
import br.com.finance.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Void> login(@Valid @RequestBody AuthenticationDto input) {
        service.authenticate(input);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDto input) {
        service.register(input);

        return ResponseEntity.ok().build();
    }

}
