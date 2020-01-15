package com.springboot.api.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import com.springboot.api.entities.Module;
import com.springboot.api.utils.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * UserRepositoryTest
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ModuleRepositoryTest {


    @Autowired
    private ModuleRepository moduleRepository;

    @BeforeEach
    public void setUp() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(Utils.provideAuthentication());
        SecurityContextHolder.setContext(securityContext);
        
        Module module = Module.builder().name("ADMIN").build();
        this.moduleRepository.save(module);
    }

    @AfterEach
    public void tearDown() {
        this.moduleRepository.deleteAll();
    }


    @Test
    public void testFindModule() {
        Optional<Module> module = this.moduleRepository.findByName("ADMIN");
        assertNotNull(module.get());
    }

}
