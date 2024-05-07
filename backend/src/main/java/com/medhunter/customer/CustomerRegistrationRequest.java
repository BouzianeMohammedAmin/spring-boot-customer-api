package com.medhunter.customer;

public record CustomerRegistrationRequest(
        String name ,
        String email ,
        Integer age
) {
}
