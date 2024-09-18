package com.medhunter.journey;

import com.github.javafaker.Faker;
import com.medhunter.customer.Customer;
import com.medhunter.customer.CustomerRegistrationRequest;
import com.medhunter.customer.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {
@Autowired
private WebTestClient webTestClient ;


@Test
public void canRegisterCustomer(){
    //create register request
    Faker faker = new Faker() ;
    String name = faker.name().fullName() ;
    String email = faker.name().firstName()+"-"+ UUID.randomUUID() + "@xxxxxxxx.com" ;
    Integer age = faker.random().nextInt(10, 100);
    Gender gender = age % 2 ==0 ? Gender.MALE : Gender.FEMALE  ;
    CustomerRegistrationRequest request =
            new CustomerRegistrationRequest(
                    name,
                    email,
                    "password", age,
                    gender);
    //add customer
    webTestClient.post()
            .uri("/api/v1/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), CustomerRegistrationRequest.class)
            .exchange()
            .expectStatus()
            .isOk() ;
    //get all customers
  List<Customer> allCustomers;
    allCustomers = webTestClient.get()
              .uri("/api/v1/customers")
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus()
              .isOk()
              .expectBodyList(new ParameterizedTypeReference<Customer>() {
              })
              .returnResult()
              .getResponseBody();


    Customer customer = new Customer(name, email, "password", age, gender) ;

    assertThat(allCustomers).
             usingRecursiveFieldByFieldElementComparatorIgnoringFields("id" )
            .contains(customer) ;

    //get customer by id

    assert allCustomers != null;
    Long id = allCustomers.stream().filter(c->c.getEmail().equals(email)).
        map(Customer::getId).findFirst().orElseThrow() ;
customer.setId(id);

        webTestClient.get()
                .uri("api/v1/customers/{id}" , id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                }).isEqualTo(customer) ;

        //delete customer
    webTestClient.delete()
            .uri("/api/v1/customers/{id}" , id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk() ;

}

@Test
    public void canUpdateCustomer(){
    /*

Long id = 1l ;
Faker faker = new Faker() ;
String name = faker.name().fullName();
    Customer customer = new Customer(1l ,  name, 28, "ossama@gmail.com", Gender.MALE) ;

    webTestClient.put()
            .uri("/api/v1/customers/{id}" , id)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(customer), CustomerRegistrationRequest.class)
            .exchange()
            .expectStatus()
            .isOk() ;

customer.setName(name);

    webTestClient.get()
            .uri("api/v1/customers/{id}" , id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<Customer>() {
            }).isEqualTo(customer) ;
 */
}










}
