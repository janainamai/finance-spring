package br.com.authentication.controller;

import br.com.authentication.dto.RegisterUserDto;
import br.com.authentication.service.UserService;
import br.com.authentication.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserValidator validator;

    @PostMapping()
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDto userDto) {
        validator.validateSamePassword(userDto.getPassword(), userDto.getConfirmPassword());
        validator.validateUsernameAlreadyExists(userDto.getUsername());

        service.registerUserAccount(userDto.toEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
