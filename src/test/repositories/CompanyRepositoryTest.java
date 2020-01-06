package com.springboot.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import com.springboot.api.entities.Company;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserRepositoryTest
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CompanyRepositoryTest {


    @Autowired
    private CompanyRepository companyRepository;

    private Company company;
    private Pageable pageable;

    @Before
    public void setUp() {
        this.pageable = PageRequest.of(0, 25, Sort.by(Order.asc("name"), Order.desc("id")));

        company = Company.builder().name("Company XYZ").build();
        this.companyRepository.save(company);
    }

    @After
    public void tearDown() {
        this.companyRepository.deleteAll();
    }

    
    @Test
    public void testFindCopmanyById() {
        Optional<Company> companyOp = this.companyRepository.findById(this.company.getId());
        assertNotNull(companyOp.get());
    }
    
    @Test
    public void testFindCopmanyByName() {
        List<Company> companies = this.companyRepository.findByName("Company XYZ", this.pageable);
        assertEquals(1, companies.size());
    }

}
