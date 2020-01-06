package com.springboot.api.repositories;

import java.util.Optional;

import com.springboot.api.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * RoleRepository
 */
@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
@Transactional(readOnly=true)
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(@Param("name") String name);

}
