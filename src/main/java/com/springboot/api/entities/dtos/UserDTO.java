package com.springboot.api.entities.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.api.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * UserDTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Long id;

    @NotNull(message = "{firstname.required}")
    private String firstname;
    private String lastname;
    private String password;

    @NotNull(message = "{email.required}")
    private String email;

    private List<RoleDTO> roles;
    private List<ModuleDTO> modules;
    private String image;
    private CompanyDTO company;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime createDate;

    private String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime updateDate;
    private Integer version;

    public static UserDTO parseUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .company(CompanyDTO.parseCompanyDTO(user.getCompany()))
                .roles(user.getRoles()
                        .stream()
                        .map(RoleDTO::parseRoleDTO)
                        .collect(Collectors.toList()))
                .modules(user.getModules()
                            .stream()
                            .map(ModuleDTO::parseModuleDTO)
                            .collect(Collectors.toList()))
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .version(user.getVersion())
                .build();

    }

}
