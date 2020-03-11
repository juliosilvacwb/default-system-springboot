package com.springboot.api.repositories;

import java.util.List;
import java.util.Optional;

import com.springboot.api.entities.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserRepository
 */


@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "users", path = "users", excerptProjection = User.class)
@Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<User, Long>{

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    List<User> findAll();
    
    @RestResource(path = "by-company")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #companyId == principal.companyId")
    @Query("select distinct(u) from User u join u.company c where c.id = :companyId")
    List<User> findAllByCompanyId(@Param("companyId") Long companyId);

    @PostAuthorize("hasAuthority('ROLE_ADMIN') or (returnObject.isPresent() && returnObject.get().company.id == principal.companyId)")
    Optional<User> findById(@Param("id") Long id);

    @RestResource(path = "by-firstname")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #companyId == principal.companyId")
    @Query("select distinct(u) from User u join u.company c where c.id = :companyId and lower(u.firstname) like lower(concat('%', :firstname,'%'))")
    List<User> findByFirstname(@Param("companyId") Long companyId, @Param("firstname") String firstname, Pageable pageable);

    @RestResource(exported = false)
	Optional<User> findByEmail(String string);

}
