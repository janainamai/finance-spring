package br.com.planner.authentication.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleCreatedDto {

    private String username;
    private List<String> roles;

}
