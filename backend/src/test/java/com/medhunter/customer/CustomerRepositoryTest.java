package com.medhunter.customer;

import com.medhunter.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//without extend abstract class we us ower data base and notest testcontainer
class CustomerRepositoryTest  extends AbstractTestContainer {

    @Autowired
    private CustomerRepository underTest ;

    @Autowired
    private ApplicationContext applicationContext ;


    @BeforeEach
    void setUp() {
    }

    @Test
    void existsCustomerByEmail() {

        String email =  faker.internet().safeEmailAddress()+ "-"+ UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(), email, "password", 20,

                Gender.MALE) ;

        underTest.save(customer);


        boolean actual = underTest.existsCustomerByEmail(email) ;
        assertThat(actual).isTrue() ;
    }

    @Test
    void existsCustomerById() {

        String email =  faker.internet().safeEmailAddress()+ "-"+ UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(), email, "password", 20,

                Gender.MALE) ;

        underTest.save(customer);


        Long id =  underTest.findAll().stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
    boolean actual = underTest.existsCustomerById(id) ;



        assertThat(actual).isTrue() ;

    }



    @Test
    void existsCustomerByEmailFalseWhenNotExist() {

        String email =  faker.internet().safeEmailAddress()+ "-"+ UUID.randomUUID();

        boolean actual = underTest.existsCustomerByEmail(email) ;
        assertThat(actual).isFalse() ;
    }

    @Test
    void existsCustomerByIdFalseWhenNotExist() {

        Long id =  -1l;
        boolean actual = underTest.existsCustomerById(id) ;

        assertThat(actual).isFalse() ;

    }
}