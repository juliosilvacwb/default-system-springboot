package com.springboot.api.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.api.entities.dtos.CompanyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Company
 */
@Entity
@Table(name="company")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

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

	public static Company parseCompany(CompanyDTO companyDTO) {
        return Company.builder()
            .id(companyDTO.getId())
            .name(companyDTO.getName())
            .createDate(companyDTO.getCreateDate())
            .updateDate(companyDTO.getUpdateDate())
            .version(companyDTO.getVersion())
            .build();
	}

}
