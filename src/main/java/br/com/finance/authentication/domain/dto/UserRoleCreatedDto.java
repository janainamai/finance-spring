package br.com.finance.authentication.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleCreatedDto {

    private String login;
    private List<String> roles;

}
