package br.com.finance.authentication.controllers;

import br.com.finance.authentication.domain.entities.RoleEntity;
import br.com.finance.authentication.controllers.client.input.CreateUserRoleInput;
import br.com.finance.authentication.services.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationRoleController {

    @Autowired
    private UserRoleService roleService;

    @GetMapping("/role")
    public ResponseEntity<List<RoleEntity>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping("/role")
    public ResponseEntity<Void> createUserRole(@Valid @RequestBody CreateUserRoleInput input) {
        roleService.saveUserRole(input.toDto());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
