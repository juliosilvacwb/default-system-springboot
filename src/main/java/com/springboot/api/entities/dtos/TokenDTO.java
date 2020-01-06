package com.springboot.api.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * TokenDto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenDTO {
    private String token;
}