package com.elisporter;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;



@Entity
@Slf4j
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    private RegistrationOrigin registrationOrigin;

    public Customer() {
    }

    public Customer(Integer id, String name, RegistrationOrigin registrationOrigin) {
        this.id = id;
        this.name = name;
        this.registrationOrigin = registrationOrigin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegistrationOrigin getRegistrationOrigin() {
        return registrationOrigin;
    }

    public void setRegistrationOrigin(RegistrationOrigin registrationOrigin) {
        log.info("this is logging");
        this.registrationOrigin = registrationOrigin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id)
                && Objects.equals(name, customer.name)
                && registrationOrigin == customer.registrationOrigin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, registrationOrigin);
    }
}
