package com.springboot.api.entities.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.api.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
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
        return UserDTO.builder().id(user.getId()).firstname(user.getFirstname()).lastname(user.getLastname())
                .email(user.getEmail()).company(CompanyDTO.parseCompanyDTO(user.getCompany()))
                .roles(user.getRoles().stream().map(RoleDTO::parseRoleDTO).collect(Collectors.toList()))
                .modules(user.getModules().stream().map(ModuleDTO::parseModuleDTO).collect(Collectors.toList()))
                .createDate(user.getCreateDate()).updateDate(user.getUpdateDate()).version(user.getVersion()).build();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            jsonString = super.toString();
        }
        return jsonString;
    }


    

}
