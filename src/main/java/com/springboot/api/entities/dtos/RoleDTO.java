package com.springboot.api.entities.dtos;

import com.springboot.api.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * RoleDTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO {

    private String name;

    public static RoleDTO parseRoleDTO(Role role) {
        return RoleDTO.builder()
                .name(role.getName())
                .build();
    }

}
