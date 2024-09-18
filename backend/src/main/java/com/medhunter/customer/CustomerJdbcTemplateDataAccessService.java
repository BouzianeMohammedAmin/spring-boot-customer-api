package com.medhunter.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jdbc")
public class CustomerJdbcTemplateDataAccessService implements CustomerDao{

private final JdbcTemplate jdbcTemplate ;
private final CustomerRowMapper customerRowMapper ;
    public CustomerJdbcTemplateDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        String sql = """
                SELECT id , name , email ,password , age , gender
                 FROM customer 
                """ ;
      /*  RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
            Customer customer = new Customer(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("email")
            );
            return customer;
        };
        List<Customer> customers =  jdbcTemplate.query(sql , customerRowMapper);
// inline
         return jdbcTemplate.query(sql , (rs , rowNum)->
                new Customer(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email")
                ));   */
// use class costumize
        return jdbcTemplate.query(sql ,customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long  id) {
        String sql = """
                SELECT id , name , email ,password, age , gender FROM customer WHERE id=?
                """ ;
        return jdbcTemplate.query(sql , customerRowMapper , id).stream().findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {

        String sql = """
                INSERT INTO customer(name , email ,password, age , gender)
                 VALUES(? , ? , ? , ? , ?  )
                """ ;
        int reslt = jdbcTemplate.update(sql , customer.getName() , customer.getEmail() , customer.getPassword(),customer.getAge() , customer.getGender().name()
        ) ;

    }

    @Override
    public boolean existPersonWithEmail(String email) {
/*
        String sql = """
                SELECT id FROM customer WHERE email=?
                """ ;

        return jdbcTemplate.query(sql, customerRowMapper, email).stream().findFirst().isEmpty();

*/
        String sql = """
                SELECT count(id)FROM customer WHERE email=?
                """ ;

        Long count = jdbcTemplate.queryForObject(sql , Long.class , email) ;
        return count!=null && count>0 ;

    }

    @Override
    public void deleteCustomerById(Long id) {

        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id) ;
    }

    @Override
    public boolean existPersonWithId(Long id) {
        String sql = """
                SELECT id , name , email ,password ,  age , gender FROM customer WHERE id=?
                """ ;

        boolean b = ! jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst().isEmpty();
        return   b;

    }

    @Override
    public void updateCustomer(Customer customer) {
if(customer.getName() != null){
    String sql = """
                UPDATE customer SET name = ?  WHERE id=?
                """ ;
    jdbcTemplate.update(sql , customer.getName() ,customer.getId() ) ;
}

        if(customer.getAge() != null){
            String sql = """
                UPDATE customer SET age= ?  WHERE id=?
                """ ;
            jdbcTemplate.update(sql , customer.getAge(),customer.getId() ) ;
        }

        if(customer.getEmail() != null){
            String sql = """
                UPDATE customer SET email= ?  WHERE id=?
                """ ;
            jdbcTemplate.update(sql , customer.getEmail(),customer.getId() ) ;
        }



    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String Email) {
        return Optional.empty();
    }
}
