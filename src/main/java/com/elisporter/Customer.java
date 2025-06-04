package com.elisporter;

import java.util.Objects;

public class Customer {

    private Integer id;
    private String name;
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
