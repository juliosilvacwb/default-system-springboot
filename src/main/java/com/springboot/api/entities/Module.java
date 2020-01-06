package com.springboot.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.springboot.api.entities.dtos.ModuleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Module
 */
@Entity
@Table(name="module")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Module {

    @Id
    private String name;

    public static Module parseModule(ModuleDTO moduleDTO) {
        return Module.builder().name(moduleDTO.getName()).build();
    }
}
