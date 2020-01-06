package com.springboot.api.entities.dtos;

import com.springboot.api.entities.Module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ModuleDTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModuleDTO {

    private String name;

    public static ModuleDTO parseModuleDTO(Module module) {
        return ModuleDTO.builder()
                .name(module.getName())
                .build();
    }

}
