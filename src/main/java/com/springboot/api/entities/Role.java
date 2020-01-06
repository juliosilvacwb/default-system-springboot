package com.springboot.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.api.entities.dtos.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Role
 */
@Entity
@Table(name="role")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {

    @Id
    @JsonProperty("name")
    private String name;

    public static Role parseRole(RoleDTO roleDTO) {
        return Role.builder().name(roleDTO.getName()).build();
    }
    
}
