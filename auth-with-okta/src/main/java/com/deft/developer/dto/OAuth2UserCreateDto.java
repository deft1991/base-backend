package com.deft.developer.dto;

import lombok.Data;

/*
 * Created by sgolitsyn on 5/5/20
 */
@Data
public class OAuth2UserCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
