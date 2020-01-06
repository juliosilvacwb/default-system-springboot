package com.springboot.api.repositories;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import com.springboot.api.entities.Role;

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
public class RoleRepositoryTest {


    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {

        Role role = Role.builder().name("ADMIN").build();
        this.roleRepository.save(role);

    }

    @After
    public void tearDown() {
        this.roleRepository.deleteAll();
    }

    @Test
    public void testFindRole() {
        Optional<Role> role = this.roleRepository.findByName("ADMIN");
        assertNotNull(role.get());
    }

}
