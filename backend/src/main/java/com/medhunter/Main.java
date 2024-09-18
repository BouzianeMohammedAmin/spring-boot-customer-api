package com.medhunter;
import com.medhunter.customer.Customer;
import com.medhunter.customer.CustomerRepository;
import com.medhunter.customer.Gender;
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


        /*
        String [] benDef = configurableApplicationContext.getBeanDefinitionNames() ;
        for (String name : benDef) {
            System.out.println("-----> " + name);
        }*/


    }



    /* possible use it with implements CommandLinRunner and impl methode run */
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){

        return args ->{
            Customer ossama = new Customer(1L, "Ossama" , "ossama@gmail.com", "password", 28 , Gender.MALE) ;
           // Customer amine = new Customer(2L, "amine" , 26 , "aminez@gmail.com") ;
            Customer hato = new Customer(3L, "hato" , "haaato@gmail.com", "password", 41 , Gender.MALE) ;
            List<Customer> customers = List.of(ossama , hato) ;
           // customerRepository.saveAll(customers) ;

        };}


}
