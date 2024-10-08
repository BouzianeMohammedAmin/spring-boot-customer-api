package com.medhunter.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer , Long> {

boolean existsCustomerByEmail(String email) ;
boolean existsCustomerById(Long id) ;

Optional<Customer> getCustomersByEmail(String email ) ;
}
