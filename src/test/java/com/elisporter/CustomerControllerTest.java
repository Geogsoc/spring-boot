package com.elisporter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCustomersReturnsCustomersSuccessfully() throws Exception {

        List<Customer> mockCustomers = List.of(new Customer(1, "Alice", RegistrationOrigin.CASINO));

        when(customerService.getCustomers()).thenReturn(mockCustomers);

        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));

        verify(customerService).getCustomers();
        verifyNoMoreInteractions(customerService);

    }

    @Test
    void addCustomer() throws Exception {

        Customer mockCustomer = new Customer(1, "Alice", RegistrationOrigin.CASINO);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isOk());

        verify(customerService).insertCustomer(refEq(mockCustomer));
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void updateCustomerValid() throws Exception {


        Customer mockCustomer = new Customer(1, "Alice", RegistrationOrigin.CASINO);

        mockMvc.perform(patch("/api/v1/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isOk());


        verify(customerService).updateCustomer(refEq(mockCustomer), eq(1));
        verifyNoMoreInteractions(customerService);

    }

    @Test
    void updateCustomerInvalidId() throws Exception {

        Customer mockCustomer = new Customer(1, "Alice", RegistrationOrigin.CASINO);

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with ID 1 not found"))
                .when(customerService)
                .updateCustomer(mockCustomer, 1);

        mockMvc.perform(patch("/api/v1/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().is4xxClientError());

        verify(customerService).updateCustomer(argThat(customer ->
                customer.getName().equals("Alice") &&
                        customer.getRegistrationOrigin() == RegistrationOrigin.CASINO
        ), eq(1));


        verifyNoMoreInteractions(customerService);

    }

    @Test
    void findCustomerById() {
    }

    @Test
    void deleteCustomerById() {
    }
}