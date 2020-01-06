package com.springboot.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.springboot.api.entities.Company;
import com.springboot.api.entities.Module;
import com.springboot.api.entities.Role;
import com.springboot.api.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserRepositoryTest
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthenticationRepository authenticationRepository;
   
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company company;
    private User user;
    private Pageable pageable;

    @Before
    public void setUp() {

        this.pageable = PageRequest.of(0, 25, Sort.by(Order.asc("firstname"), Order.desc("id")));

        Role role = Role.builder().name("ADMIN").build();
        this.roleRepository.save(role);
        List<Role> roles = Arrays.asList(new Role[] {role});

        Module module = Module.builder().name("ADMIN").build();
        this.moduleRepository.save(module);
        List<Module> modules = Arrays.asList(new Module[] {module});

        company = Company.builder().name("Company XYZ").build();
        this.companyRepository.save(company);

        user = User.builder()
                        .firstname("Fulano")
                        .email("fulano@email.com")
                        .password(passwordEncoder.encode("live0102"))
                        .company(company)
                        .roles(roles)
                        .modules(modules)
                        .image("//link/imagem")
                        .build();

        this.userRepository.save(user);
    }

    @After
    public void tearDown() {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
        this.moduleRepository.deleteAll();
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOp = this.userRepository.findById(this.user.getId());
        assertNotNull(userOp.get());
        assertNotNull(userOp.get().getCompany());
        assertEquals(1, userOp.get().getModules().size());
        assertEquals(1, userOp.get().getRoles().size());
    }
    
    @Test
    public void testFindUserByFirstname() {
        List<User> users = this.userRepository.findByFirstname(this.company.getId(), "Fulano", this.pageable);
        assertEquals(1, users.size());
        assertNotNull(users.get(0));
        assertNotNull(users.get(0).getCompany());
        assertEquals(1, users.get(0).getModules().size());
        assertEquals(1, users.get(0).getRoles().size());
    }
   
    @Test
    public void testFindUserByEmail() {
        Optional<User> user = this.authenticationRepository.findByEmail("fulano@email.com");
        assertNotNull(user.get());
        assertNotNull(user.get().getCompany());
        assertTrue(passwordEncoder.matches("live0102", user.get().getPassword()));
    }
   
}
