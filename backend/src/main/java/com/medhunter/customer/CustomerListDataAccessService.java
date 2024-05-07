package com.medhunter.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements  CustomerDao{
    private static final List<Customer> customers  ;
    static  {
        customers = new ArrayList<Customer>() ;
        Customer ossama = new Customer(1L, "Ossamalist" , 28 , "ossama@gmail.com") ;
        customers.add(ossama) ;
        Customer amine = new Customer(2L, "aminelist" , 26 , "amine@gmail.com") ;
        customers.add(amine) ;
        Customer hato = new Customer(3L, "hatolist" , 41 , "hato@gmail.com") ;
        customers.add(hato) ;
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customers ;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
      return customers.stream().
                filter(c-> c.getId().equals(id)).
                findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer) ;
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        return customers.stream().
                anyMatch(customer -> customer.getEmail().equals(email)) ;

       /* return ! customers.stream().
                filter(customer -> customer.getEmail().equals(email)).
                toList().isEmpty();
        */
    }

    @Override
    public void deleteCustomerById(Long id) {

        customers.stream().filter(customer -> customer.getId().equals(id))
                .findFirst().ifPresent(customers::remove);//customer -> customers.remove(customer)

    }

    @Override
    public boolean existPersonWithId(Long id) {
        return customers.stream().anyMatch(customer -> customer.getId().equals(id)) ;
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.stream().filter(customer1 -> customer1.getId().equals(customer.getId()))
                .findFirst().ifPresent(customer1 -> {
                    customer1.setName(customer.getName());
                    customer1.setAge(customer.getAge());
                    customer1.setEmail(customer.getEmail());
                });
    }

    /*
  // my implementation for delete customer
    @Override
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }
   */


}
