package com.medhunter.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerJPADataAccessServiceTest {
private CustomerJPADataAccessService underTest ;
private  AutoCloseable autoCloseable ;
@Mock
private CustomerRepository customerRepository ;

    @BeforeEach
    void setUp() {
       autoCloseable= MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
   //when this method run test if findAll
        // in customerRipository its invoked
        underTest.selectAllCustomers() ;

        verify(customerRepository).findAll() ;


    }

    @Test
    void selectCustomerById() {
        // test if argm id it used
        //given
        Long id = 1L  ;

        //when
        underTest.selectCustomerById(id) ;

        //then

        verify(customerRepository).findById(id) ;

    }

    @Test
    void insertCustomer() {
        Customer customer = new Customer(
                1L ,
                "amine" ,
                23,
                "mail"
        );

        underTest.insertCustomer(customer);

        verify(customerRepository).save(customer) ;

    }

    @Test
    void existPersonWithEmail() {
        //given
        String mail = "mail"  ;

        //when
        underTest.existPersonWithEmail(mail) ;

        //then

        verify(customerRepository).existsCustomerByEmail(mail) ;

    }

    @Test
    void deleteCustomerById() {
        //given
        Long id = 1L  ;

        //when
        underTest.deleteCustomerById(id); ;

        //then

        verify(customerRepository).deleteById(id); ;



    }

    @Test
    void existPersonWithId() {

        //given
        Long id = 1L  ;

        //when
        underTest.existPersonWithId(id) ;

        //then

        verify(customerRepository).existsCustomerById(id) ;

    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(
                1L ,
                "amine" ,
                23,
                "mail"
        );

        underTest.updateCustomer(customer);

        verify(customerRepository).save(customer) ;

    }
}