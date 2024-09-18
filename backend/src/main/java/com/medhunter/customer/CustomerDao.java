package com.medhunter.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers() ;
    Optional<Customer> selectCustomerById(Long id) ;

    void insertCustomer(Customer customer) ;

    boolean existPersonWithEmail(String email) ;

    void deleteCustomerById(Long id) ;

    boolean existPersonWithId(Long id) ;

    void updateCustomer(Customer customer) ;

    Optional<Customer> selectCustomerByEmail(String Email) ;

    //my implementation
    // void deleteCustomer(Customer customer ) ;

}
