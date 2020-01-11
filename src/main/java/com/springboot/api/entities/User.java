package com.springboot.api.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.springboot.api.entities.dtos.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * User
 */
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "Company_id" }) })
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{firstname.required}")
    @Column(nullable = false)
    private String firstname;
    private String lastname;

    @NotNull(message = "{email.required}")
    @Column(nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id", nullable = false)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn(nullable = false)
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn(nullable = false)
    private List<Module> modules;

    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime updateDate;

    @Version
    private Integer version;

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime date = LocalDateTime.now();
        this.createDate = date;
        this.updateDate = date;
    }

	public static User parseUser(UserDTO userDTO) {
        return User.builder()
                .company(Company.parseCompany(userDTO.getCompany()))
                .createDate(userDTO.getCreateDate())
                .updateDate(userDTO.getUpdateDate())
                .email(userDTO.getEmail())
                .firstname(userDTO.getFirstname())
                .id(userDTO.getId())
                .image(userDTO.getImage())
                .lastname(userDTO.getLastname())
                .password(userDTO.getPassword())
                .version(userDTO.getVersion())
                .modules(userDTO.getModules().stream().map(Module::parseModule).collect(Collectors.toList()))
                .roles(userDTO.getRoles().stream().map(Role::parseRole).collect(Collectors.toList()))
                .build();
	}

}
