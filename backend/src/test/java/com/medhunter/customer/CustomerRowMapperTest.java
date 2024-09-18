package com.medhunter.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {

        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class) ;

        when(resultSet.getLong("id")).thenReturn(1L) ;
        when(resultSet.getInt("age")).thenReturn(12) ;
        when(resultSet.getString("name")).thenReturn("moh") ;
        when(resultSet.getString("email")).thenReturn("moh@gmail.com") ;
        when(resultSet.getString("gender")).thenReturn("FEMALE") ;


        Customer customer = customerRowMapper.mapRow(resultSet , 1) ;


        Customer expected = new Customer(
                1l ,
                "moh" ,
                "moh@gmail.com", "password", 12,
                Gender.FEMALE);

    assertThat(customer).isEqualTo(expected) ;

    }
}