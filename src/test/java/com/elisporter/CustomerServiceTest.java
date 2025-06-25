package com.elisporter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;
    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);

        customerService = new CustomerService(customerRepository);
    }

    @Test
    void shouldReturnAllCustomer() {
        List<Customer> customers = List.of(
                new Customer(1, "Alice", RegistrationOrigin.CASINO)
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomers();

        assertEquals(1, result.size());

    }

    @Test
    void insertCustomer() {

        Customer customer = new Customer(42, "Elis", RegistrationOrigin.BET);

        // Act
        customerService.insertCustomer(customer);

        verify(customerRepository).save(customer);

    }

    @Test
    void findCustomer() {


    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomer() {
    }
}