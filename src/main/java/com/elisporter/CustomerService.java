package com.elisporter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return customerRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " Not found"));
    }

    public void deleteCustomer(Integer id) {

        if (!customerRepository.existsById(id)) {
            throw new IllegalStateException("Customer with ID " + id + " not found");
        }


        customerRepository.deleteById(id);
    }

    public void updateCustomer(Customer customer, Integer id) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Customer with ID " + id + " not found"));

        existingCustomer.setName(customer.getName());
        existingCustomer.setRegistrationOrigin(customer.getRegistrationOrigin());

        customerRepository.save(existingCustomer);
    }
}
