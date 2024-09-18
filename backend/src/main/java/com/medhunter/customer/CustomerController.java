package com.medhunter.customer;

import com.medhunter.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final  CustomerService customerService ;

    private final JWTUtil jwtUtil ;

    public CustomerController(CustomerService customerService, JWTUtil  jwtU) {
        this.customerService = customerService;
        this.jwtUtil = jwtU;
    }

/*
    @RequestMapping(
            path ="/" ,
            method = RequestMethod.GET
    )
*/
    @GetMapping
    public List<Customer> getAllCustomers (){

        return customerService.getAllCustomer() ;
    }


    @GetMapping("{id}")
    public Customer getCustomer (@PathVariable(value = "id")  Long id){
        return customerService.getCustomer(id) ;
    }

@PostMapping
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRegistrationRequest request){

     customerService.addCustomer(request);
     String jwtToken = jwtUtil.issueToken(request.email() , "ROLE_USER") ;
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION , jwtToken)
                .build() ;

    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id ){
        customerService.deleteCustomerById(id);
    }

        @PutMapping("{id}")
    public void updateCustomer(@PathVariable(value = "id") Long id  , @RequestBody CustomerUpdateRequest request  ){
        customerService.updateCustomerById(id , request);
    }


    /*
    @GetMapping("/customer") // /customer?id=....
    public Customer getCustomer (@RequestParam(value = "id") Integer id){
        Customer customer =null;

        for(Customer c :customers){
            if(c.getId() == id) {
                customer = c ;
                break  ;
            }
        }
        return customer ;
    }
*/

}
