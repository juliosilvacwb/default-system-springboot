package com.springboot.api.repositories;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import com.springboot.api.entities.Module;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserRepositoryTest
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ModuleRepositoryTest {


    @Autowired
    private ModuleRepository moduleRepository;

    @Before
    public void setUp() {
        Module module = Module.builder().name("ADMIN").build();
        this.moduleRepository.save(module);
    }

    @After
    public void tearDown() {
        this.moduleRepository.deleteAll();
    }


    @Test
    public void testFindModule() {
        Optional<Module> module = this.moduleRepository.findByName("ADMIN");
        assertNotNull(module.get());
    }

}
