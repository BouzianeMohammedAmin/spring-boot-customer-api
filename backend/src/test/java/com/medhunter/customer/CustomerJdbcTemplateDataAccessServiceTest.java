package com.medhunter.customer;

import com.medhunter.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJdbcTemplateDataAccessServiceTest extends AbstractTestContainer {

    private CustomerJdbcTemplateDataAccessService underTest ;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper() ;


    @BeforeEach
    void setUp() {
        underTest=new CustomerJdbcTemplateDataAccessService(
                getJdbcTemplate() ,
                customerRowMapper
        ) ;
    }

    @Test
    void selectAllCustomers() {

        Customer customer = new Customer(
                20, faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID(), faker.name().fullName()

        ) ;

        underTest.insertCustomer(customer);

        List<Customer> actual = underTest.selectAllCustomers();
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        String email =  faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID();
        Customer customer = new Customer(
                20, email, faker.name().fullName()

        ) ;

        underTest.insertCustomer(customer);


       Long id =  underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id) ;



        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName()) ;
            assertThat(c.getEmail()).isEqualTo(customer.getEmail()) ;
            assertThat(c.getAge()).isEqualTo(customer.getAge()) ;


        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerByIdNoteFound() {

        Customer customer = new Customer(
                20, faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID(), faker.name().fullName()

        ) ;

        underTest.insertCustomer(customer);

        Long id= 0L;

         Optional<Customer> actual = underTest.selectCustomerById(id) ;

        assertThat(actual).isEmpty();
    }

    @Test
    void existPersonWithEmail() {
        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);
        boolean actual= underTest.existPersonWithEmail(fakeEmail);
        assertThat(actual).isTrue() ;

    }
    @Test
    void existPersonWithEmailWillReturnFalseWhenDoesNotExist() {
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;

        boolean actual = underTest.existPersonWithEmail(fakeEmail) ;

        assertThat(actual).isFalse() ;
    }


    @Test
    void existPersonWithID() {
        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();

        boolean actual = underTest.existPersonWithId(id) ;
        assertThat(actual).isTrue() ;

    }

    @Test
    void existPersonWithIDWillReturnFalseWhenIdNull() {
        Long id= null ;
        boolean actual = underTest.existPersonWithId(id) ;
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {

        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();

      underTest.deleteCustomerById(id);
      boolean actual = underTest.existPersonWithId(id);
        assertThat(actual).isFalse() ;
    }

    @Test
    void updateCustomerName() {
        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();

        String newName="new name " ;

        Customer update = new Customer() ;
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id); ;
        assertThat(actual).isPresent().hasValueSatisfying(c->{
           assertThat(c.getId()).isEqualTo(id);
           assertThat(c.getName()).isEqualTo(newName) ;
            assertThat(c.getEmail()).isEqualTo(customer.getEmail()) ;
            assertThat(c.getAge()).isEqualTo(customer.getAge()) ;
        });

    }

    @Test
    void updateCustomerEmail() {
        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();

        String newEmail="new Email " ;

        Customer update = new Customer() ;
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id); ;
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail) ;
            assertThat(c.getName()).isEqualTo(customer.getName()) ;
            assertThat(c.getAge()).isEqualTo(customer.getAge()) ;
        });


    }

    @Test
    void updateCustomerAge() {
        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();

Integer newAge = 23;
        Customer update = new Customer() ;
        update.setId(id);
        update.setAge(newAge);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id); ;
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail()) ;
            assertThat(c.getName()).isEqualTo(customer.getName()) ;
            assertThat(c.getAge()).isEqualTo(newAge) ;
        });


    }


    @Test
    void WillUpdateAllPropertiesCustomer() {
        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();


        Customer update = new Customer() ;
        update.setId(id);
        update.setEmail("newEmail");
        update.setAge(23);
        update.setName("new name");

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id); ;
        assertThat(actual).isPresent().hasValue(update) ;



    }


    @Test
    void WillNotUpdateWhenNothingToUpdate() {

        String fakeName =  faker.name().fullName() ;
        String fakeEmail = faker.internet().safeEmailAddress()+ "-"+UUID.randomUUID() ;
        Customer customer = new Customer(
                20, fakeEmail, fakeName
        ) ;
        underTest.insertCustomer(customer);

        Long id=underTest.selectAllCustomers().stream()
                .filter(c->c.getEmail().equals(fakeEmail)).
                map(Customer::getId).findFirst().orElseThrow();


        Customer update = new Customer() ;
        update.setId(id);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id); ;
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail()) ;
            assertThat(c.getName()).isEqualTo(customer.getName()) ;
            assertThat(c.getAge()).isEqualTo(customer.getAge()) ;
        });


    }
}