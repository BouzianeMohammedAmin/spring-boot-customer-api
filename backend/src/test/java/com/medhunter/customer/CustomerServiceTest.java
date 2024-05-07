package com.medhunter.customer;

import com.medhunter.exception.DuplicateResourceException;
import com.medhunter.exception.NoteFoundResourceExecption;
import com.medhunter.exception.RequestValidateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class) // use this for remove AutoCloseable for initial mock
class CustomerServiceTest {
    @Mock
    private CustomerDao customerDao ;
    private CustomerService underTest ;
    private   AutoCloseable autoCloseable ;


    @BeforeEach
    void setUp() {
         autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao) ;
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCustomer() {

        underTest.getAllCustomer() ;

        verify(customerDao).selectAllCustomers();

    }

    @Test
    void canGetCustomer() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;


        Customer actual =  underTest.getCustomer(id) ;

        assertThat(actual).isEqualTo(customer) ;


    }
    @Test
    void WillThrowWhenGetCustomerReturnEmptyOptional() {
        Long id = 10L;

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty()) ;



        assertThatThrownBy(()->underTest.getCustomer(id))
                .isInstanceOf(NoteFoundResourceExecption.class)
                .hasMessage("no exist customer with this id [%s]".formatted(id)) ;

    }

    @Test
    void addCustomer() {

        String email = "test@mail.com" ;


        when(customerDao.existPersonWithEmail(email)).thenReturn(false) ;

        CustomerRegistrationRequest request = new CustomerRegistrationRequest
                ("bouziane", "test@mail.com1", 13);
        underTest.addCustomer(request);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class) ;
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer customerCaptured = customerArgumentCaptor.getValue() ;

        assertThat(customerCaptured.getId()).isNull();
        assertThat(customerCaptured.getName()).isEqualTo(request.name());
        assertThat(customerCaptured.getEmail()).isEqualTo(request.email());
        assertThat(customerCaptured.getAge()).isEqualTo(request.age());
    }
    @Test
    void willThrowWhenEmailExistWhileAddingCustomer() {

        String email = "test@mail.com" ;

        CustomerRegistrationRequest request = new CustomerRegistrationRequest
                ("bouziane", email, 13);


        when(customerDao.existPersonWithEmail(email)).thenReturn(true) ;

        assertThatThrownBy(()->underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("the email [%s] exist in another customer".formatted(request.email())) ;

        verify(customerDao , never()).insertCustomer(any());


    }
    @Test
    void deleteCustomerById() {
    Long id = 10L ;

    when(customerDao.existPersonWithId(id)).thenReturn(true) ;

    underTest.deleteCustomerById(id);

    verify(customerDao).deleteCustomerById(id);
    }
    @Test
    void WillThrowDeleteCustomerByIdWhenNotR_Exist() {
        Long id = 10L ;

        when(customerDao.existPersonWithId(id)).thenReturn(false) ;

        assertThatThrownBy(()->underTest.deleteCustomerById(id))
                .isInstanceOf(NoteFoundResourceExecption.class)
                .hasMessage("the customer wont delete it with id[%s] do not exist  ".formatted(id)) ;
        verify(customerDao , never()).deleteCustomerById(id);



    }


    @Test
    void canUpdateAllPropertyUpdateCustomerById() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
    CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
            "mohammed" ,
             email
             ,
            12
    ) ;
    when(customerDao.existPersonWithEmail(email)).thenReturn(false) ;

        underTest.updateCustomerById(id , updateRequest);
ArgumentCaptor<Customer> argumentCaptor =
        ArgumentCaptor.forClass(Customer.class) ;
verify(customerDao).updateCustomer(argumentCaptor.capture());
Customer customerCaptured = argumentCaptor.getValue();


        assertThat(customerCaptured.getAge()).isEqualTo(customer.getAge());
        assertThat(customerCaptured.getEmail()).isEqualTo(customer.getEmail());
        assertThat(customerCaptured.getName()).isEqualTo(customer.getName());

    }

    @Test
    void CanUpdateOnlyNamePropertyUpdateCustomerById() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                "mohammed" ,
                email
                ,
                12
        ) ;
        when(customerDao.existPersonWithEmail(email)).thenReturn(false) ;

        underTest.updateCustomerById(id , updateRequest);
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class) ;
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer customerCaptured = argumentCaptor.getValue();


        assertThat(customerCaptured.getAge()).isEqualTo(customer.getAge());
        assertThat(customerCaptured.getEmail()).isEqualTo(customer.getEmail());
        assertThat(customerCaptured.getName()).isEqualTo(customer.getName());

    }

    @Test
    void updateCustomerById() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                "mohammed" ,
                email
                ,
                12
        ) ;
        when(customerDao.existPersonWithEmail(email)).thenReturn(false) ;

        underTest.updateCustomerById(id , updateRequest);
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class) ;
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer customerCaptured = argumentCaptor.getValue();



        assertThat(customerCaptured.getAge()).isEqualTo(updateRequest.age());
        assertThat(customerCaptured.getEmail()).isEqualTo(updateRequest.email());
        assertThat(customerCaptured.getName()).isEqualTo(updateRequest.name());

    }

    @Test
    void canUpdateOnlyAgeProperty() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                null ,
                null
                ,
                13
        ) ;

        underTest.updateCustomerById(id , updateRequest);
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class) ;
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer customerCaptured = argumentCaptor.getValue();



        assertThat(customerCaptured.getAge()).isEqualTo(customer.getAge());
        assertThat(customerCaptured.getEmail()).isNotEqualTo(updateRequest.email());
        assertThat(customerCaptured.getName()).isNotEqualTo(updateRequest.name());

    }
    @Test
    void canUpdateOnlyEmailProperty() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                null ,
                email
                ,
                null
        ) ;
        when(customerDao.existPersonWithEmail(email)).thenReturn(false) ;

        underTest.updateCustomerById(id , updateRequest);
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class) ;
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer customerCaptured = argumentCaptor.getValue();



        assertThat(customerCaptured.getAge()).isNotEqualTo(updateRequest.age());
        assertThat(customerCaptured.getEmail()).isEqualTo(customer.getEmail());
        assertThat(customerCaptured.getName()).isNotEqualTo(updateRequest.name());

    }

    @Test
    void canUpdateOnlyNameProperty() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                "moh" ,
                null
                ,
                null
        ) ;

        underTest.updateCustomerById(id , updateRequest);
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class) ;
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer customerCaptured = argumentCaptor.getValue();



        assertThat(customerCaptured.getAge()).isNotEqualTo(updateRequest.age());
        assertThat(customerCaptured.getEmail()).isNotEqualTo(updateRequest.email());
        assertThat(customerCaptured.getName()).isEqualTo(customer.getName());

    }

    @Test
    void willThrowWhenEmailUpdateExistThisEmail() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email= "moh@gmail.com" ;
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                null ,
                email
                ,
                null
        ) ;
        when(customerDao.existPersonWithEmail(email)).thenReturn(true) ;

        assertThatThrownBy(()->underTest.updateCustomerById(id , updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("the email [%s] exist in another customer".formatted(email)) ;
        verify(customerDao , never()).updateCustomer(any());

    }

    @Test
    void willThrowWhenNoDataCHanged() {
        Long id = 10L;

        Customer customer = new Customer(id , "amine" ,12,"bouznaine@hmauil.com"  ) ;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer)) ;
        String email="bouznaine@hmauil.com";
        CustomerUpdateRequest  updateRequest = new CustomerUpdateRequest(
                "amine" ,
                email
                ,
                12
        ) ;
        when(customerDao.existPersonWithEmail(email)).thenReturn(true) ;

        assertThatThrownBy(()->underTest.updateCustomerById(id , updateRequest))
                .isInstanceOf(RequestValidateException.class)
                .hasMessage("no data changed exist :/  ") ;
        verify(customerDao , never()).updateCustomer(any());

    }


}