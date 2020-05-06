package com.deft.developer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * Created by sgolitsyn on 5/5/20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OAuth2UserCreateDto {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
}
