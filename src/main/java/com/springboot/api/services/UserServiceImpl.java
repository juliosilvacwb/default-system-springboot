package com.springboot.api.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.springboot.api.entities.Company;
import com.springboot.api.entities.Role;
import com.springboot.api.entities.Module;
import com.springboot.api.entities.User;
import com.springboot.api.repositories.AuthenticationRepository;
import com.springboot.api.repositories.CompanyRepository;
import com.springboot.api.repositories.ModuleRepository;
import com.springboot.api.repositories.RoleRepository;
import com.springboot.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<User> user = this.authenticationRepository.findByEmail(email);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) throws Exception {
        return userRepository.findById(id);
    }

    @Override
    public String setup() {
        
        Optional<User> userOptional = userRepository.findByEmail("setupspringboot@email.com");

        if (userOptional.isEmpty()) {
            Role role = Role.builder().name("ADMIN").build();
            this.roleRepository.save(role);
            List<Role> roles = Arrays.asList(new Role[] {role});
    
            Module module = Module.builder().name("ADMIN").build();
            this.moduleRepository.save(module);
            List<Module> modules = Arrays.asList(new Module[] {module});
    
            Company company = Company.builder().name("Company Setup Springboot").build();
            this.companyRepository.save(company);
    
            User user = User.builder()
                            .firstname("Setup User")
                            .email("setupspringboot@email.com")
                            .password(passwordEncoder.encode("setupspringboot"))
                            .company(company)
                            .roles(roles)
                            .modules(modules)
                            .image("")
                            .build();
    
            this.userRepository.save(user);

            return "User setupspringboot@email.com has been created.";
        } else {
            return "User is already created.";
        }

        
    }

}