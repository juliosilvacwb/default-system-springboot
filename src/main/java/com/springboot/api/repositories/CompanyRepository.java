package com.springboot.api.repositories;

import java.util.List;
import java.util.Optional;

import com.springboot.api.entities.Company;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * CompanyRepository
 */
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "companies", path = "companies")
@Transactional(readOnly=true)
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #id == principal.companyId")
    Optional<Company> findById(@Param("id") Long id);
    List<Company> findByName( @Param("name") String name, Pageable pageable);

}
