package br.com.authentication.controller;

import br.com.authentication.dto.AuthenticateUserDto;
import br.com.authentication.dto.RegisterUserDto;
import br.com.authentication.service.UserService;
import br.com.authentication.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserValidator validator;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDto userDto) {
        validator.validateSamePassword(userDto.getPassword(), userDto.getConfirmPassword());
        service.registerUserAccount(userDto.toEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticateUserDto userDto) {
        String token = service.authenticate(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(token);
    }

}
