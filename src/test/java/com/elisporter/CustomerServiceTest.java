package com.elisporter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void findallShouldReturnAllCustomer() {
        List<Customer> customers = List.of(
                new Customer(1, "Alice", RegistrationOrigin.CASINO),
                new Customer(2, "Ben", RegistrationOrigin.AFFILIATE)
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomers();

        verify(customerRepository).findAll();

        assertEquals(2, result.size());

        assertEquals(customers, result);

        verifyNoMoreInteractions(customerRepository);


    }

    @Test
    void insertCustomerShouldSaveCustomer() {

        Customer customer = new Customer(42, "Elis", RegistrationOrigin.BET);

        // Act
        customerService.insertCustomer(customer);

        verify(customerRepository).save(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findCustomerWithValidIdReturnsCustomer() {
        //arrange
        Customer customer = new Customer(42, "Elis", RegistrationOrigin.BET);

        when(customerRepository.findById(42)).thenReturn(Optional.of(customer));

        //act
        Customer result = customerService.findCustomer(42);

        //assert
        assertEquals(customer, result);
        verify(customerRepository).findById(42);
        verifyNoMoreInteractions(customerRepository);


    }

    @Test
    void findCustomerWithNoRecordReturns404() {
        //arrange
        int id = 4;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> customerService.findCustomer(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Customer with ID " + id + " not found"));

        verify(customerRepository).findById(4);
        verifyNoMoreInteractions(customerRepository);

    }

    @Test
    void deleteCustomerWithValidIdCallsCustomerRepositoryDeleteById() {
        int id = 4;

        customerService.deleteCustomer(id);

        verifyNoMoreInteractions(customerRepository);

    }


    @Test
    void deleteCustomerWithInValidIdCallsCustomerRepositoryDeleteById() {
        int id = 5;

        when(customerRepository.existsById(id)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> customerService.deleteCustomer(id)
        );

        verify(customerRepository).existsById(id);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Customer with ID " + id + " not found"));

        verifyNoMoreInteractions(customerRepository);

    }

    @Test
    void updateCustomerWithValidIdSavesDetails() {

        Customer updateToCustomer = new Customer(42, "Elis", RegistrationOrigin.CASINO);
        Customer currentSavedCustomer = new Customer(42, "Eliz", RegistrationOrigin.BET);

        when(customerRepository.findById(42)).thenReturn(Optional.of(currentSavedCustomer));

        customerService.updateCustomer(updateToCustomer, 42);

        verify(customerRepository).findById(42);
        verify(customerRepository).save(updateToCustomer);
        assertEquals("Elis", currentSavedCustomer.getName());
        assertEquals(RegistrationOrigin.CASINO, currentSavedCustomer.getRegistrationOrigin());

    }

    @Test
    void updateCustomerWithInValidIdSavesDetails() {

        Customer updateToCustomer = new Customer(45, "Elis", RegistrationOrigin.CASINO);

        when(customerRepository.findById(45)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> customerService.updateCustomer(updateToCustomer, 45)
        );

        verify(customerRepository).findById(45);

        verifyNoMoreInteractions(customerRepository);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Customer with ID " + 45 + " not found"));

    }

    @Test
    void sanitiseNameCorrectlyModifiesWithValidString() {

        String toBeModified = " John Bon Jovi";
        String expected = "john-bon-jovi";

        String result = customerService.sanitiseName(toBeModified);

        assertThat(result).isEqualTo(expected);

    }
}