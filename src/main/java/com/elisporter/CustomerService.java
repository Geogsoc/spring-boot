package com.elisporter;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();

    }

    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findCustomer(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer with ID " + id + " not found"));
    }

    public void deleteCustomer(Integer id) {

        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Customer with ID " + id + " not found");
        }


        customerRepository.deleteById(id);
    }

    public void updateCustomer(Customer customer, Integer id) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer with ID " + id + " not found"));

        existingCustomer.setName(customer.getName());
        existingCustomer.setRegistrationOrigin(customer.getRegistrationOrigin());

        customerRepository.save(existingCustomer);
    }
}
