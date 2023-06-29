package br.com.finance.authentication.controller;

import br.com.finance.authentication.dto.RegisterUserDto;
import br.com.finance.authentication.service.UserService;
import br.com.finance.authentication.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserValidator validator;

    @PostMapping("/new")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDto dto) {
        service.register(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
