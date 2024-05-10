package com.medhunter;
import com.medhunter.customer.Customer;
import com.medhunter.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication

public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Main.class, args);
//fin
    }



    /* possible use it with implements CommandLinRunner and impl methode run */
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){

        return args ->{
            Customer ossama = new Customer(1L, "Ossama" , 28 , "ossama@gmail.com") ;
           // Customer amine = new Customer(2L, "amine" , 26 , "aminez@gmail.com") ;
            Customer hato = new Customer(3L, "hato" , 41 , "haaato@gmail.com") ;
            List<Customer> customers = List.of(ossama , hato) ;
            customerRepository.saveAll(customers) ;

        };}


}
