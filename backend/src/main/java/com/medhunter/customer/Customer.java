package com.medhunter.customer;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
public class Customer  implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName="customer_id_seq" ,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="customer_id_seq"
    )

    private Long id ;
    @Column(
            nullable = false
    )
    private String name ;
    @Column(
            nullable = false
    )
    private Integer age ;
    @Column(
            nullable = false
    )
    private String email ;
    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Gender gender ;
    @Column(
            nullable = false
    )
    private String password;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Customer(String name, String email, String password, Integer age, Gender gender) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender=gender ;
        this.password=password ;
    }

    public Customer(Long id, String name, String email, String password, Integer age, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender ;
        this.password=password ;
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(age, customer.age) && Objects.equals(email, customer.email) && gender == customer.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, gender);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")) ;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}