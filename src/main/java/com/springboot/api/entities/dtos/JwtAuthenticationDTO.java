package com.springboot.api.entities.dtos;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * JwtAuthenticationDto
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JwtAuthenticationDTO {

    private String email;

    @NotEmpty(message = "Password is requerid")
    private String password;
}