package com.medhunter.customer;

//import org.springframework.stereotype.Component;
import com.medhunter.exception.DuplicateResourceException;
import com.medhunter.exception.NoteFoundResourceExecption;
import com.medhunter.exception.RequestValidateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
//@Component
public class CustomerService {

    private final CustomerDao customerDao ;
    private final PasswordEncoder passwordEncoder ;


    public CustomerService(@Qualifier("jpa") CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    List<Customer> getAllCustomer(){

        return customerDao.selectAllCustomers() ;
    }

    Customer getCustomer(Long id){
        return  customerDao.selectCustomerById(id).orElseThrow(
                ()->new NoteFoundResourceExecption("no exist customer with this id [%s]".formatted(id)));

    }

    void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
            // test for email exist
        if(customerDao.existPersonWithEmail(customerRegistrationRequest.email())){

            throw new DuplicateResourceException("the email [%s] exist in another customer".formatted(customerRegistrationRequest.email())) ;
        }


            // add
            customerDao.insertCustomer(
                    new Customer(
                            customerRegistrationRequest.name(), customerRegistrationRequest.email(),passwordEncoder.encode( customerRegistrationRequest.password()), customerRegistrationRequest.age(),
                            customerRegistrationRequest.gender())
            ) ;
    }


    void deleteCustomerById(Long id) {

        // test if exist customer with this id  ;
        if(!customerDao.existPersonWithId(id)){
            throw  new NoteFoundResourceExecption("the customer wont delete it with id[%s] do not exist  ".
                    formatted(id)) ;
        }
        //customer = getCustomer(id) ;

        //delete customer
        customerDao.deleteCustomerById(id);
        //customerDao.deleteCustomer(customer);
    }

    public void updateCustomerById(Long id, CustomerUpdateRequest request) {

        Customer customer = getCustomer(id) ;
        boolean isChanged = false ;

        // name
        if(request.name() != null  && ! customer.getName().equals(request.name()))
        {
            customer.setName(request.name());
            isChanged=true ;
        }
        // email
        if( request.email()!= null && ! customer.getEmail().equals(request.email()))
        {
            if(customerDao.existPersonWithEmail(request.email())){

                throw new DuplicateResourceException("the email [%s] exist in another customer".formatted(request.email())) ;
            }
            customer.setEmail(request.email());
            isChanged=true ;
        }

        // age
        if(request.age() != null  && ! customer.getAge().equals(request.age()))
        {
            customer.setAge(request.age());
            isChanged=true ;
        }

        if(!isChanged){
            throw  new RequestValidateException("no data changed exist :/  ") ;
        }

        // update
        customerDao.updateCustomer(customer);
    }
}


