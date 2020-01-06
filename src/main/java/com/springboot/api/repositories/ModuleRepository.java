package com.springboot.api.repositories;

import java.util.Optional;

import com.springboot.api.entities.Module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


/**
 * ModuleRepository
 */
@RepositoryRestResource(collectionResourceRel = "modules", path = "modules")
@Transactional(readOnly=true)
public interface ModuleRepository extends JpaRepository<Module, String> {

    Optional<Module> findByName(@Param("name") String name);

}
