package com.medhunter.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jpa")
public class CustomerJPADataAccessService  implements  CustomerDao{

    CustomerRepository customerRipository ;

    public CustomerJPADataAccessService(CustomerRepository customerRipository) {
        this.customerRipository = customerRipository;
    }

    @Override
    public List<Customer> selectAllCustomers() {

        return customerRipository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRipository.findById(id) ;
    }

    @Override
    public void insertCustomer(Customer customer) {




         customerRipository.save(customer) ;

    }

    @Override
    public boolean existPersonWithEmail(String email) {
        return  customerRipository.existsCustomerByEmail(email);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRipository.deleteById(id);

    }


    @Override
    public boolean existPersonWithId(Long id) {
        return customerRipository.existsCustomerById(id) ;
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRipository.save(customer) ;
    }




    /*
    // my implementation
    @Override
    public void deleteCustomer(Customer customer) {
        customerRipository.delete(customer);
    }
    */

}
