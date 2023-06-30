package br.com.finance.authentication.controllers;

import br.com.finance.authentication.domain.entities.Role;
import br.com.finance.authentication.domain.entities.User;
import br.com.finance.authentication.domain.dto.CreateUserRoleDto;
import br.com.finance.authentication.domain.dto.UserRoleCreatedDto;
import br.com.finance.authentication.services.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/auth")
public class AuthenticationRoleController {

    @Autowired
    private UserRoleService roleService;

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping("/role")
    public ResponseEntity<UserRoleCreatedDto> createUserRole(@Valid @RequestBody CreateUserRoleDto input) {
        User user = roleService.saveUserRole(input);

        UserRoleCreatedDto output = new UserRoleCreatedDto();
        output.setLogin(user.getLogin());
        output.setRoles(user.getRoles().stream().map(Role::getName).collect(toList()));

        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
