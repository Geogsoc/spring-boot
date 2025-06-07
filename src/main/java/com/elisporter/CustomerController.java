package com.elisporter;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    public void addCustomer(@RequestBody Customer customer) {
        customerService.insertCustomer(customer);
    }

    @PatchMapping("{id}")
    public void updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) {
        customerService.updateCustomer(customer, id);
    }

    @GetMapping("{id}")
    public Customer findCustomerById(@PathVariable Integer id) {

        return customerService.findCustomer(id);
    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable Integer id) {

         customerService.deleteCustomer(id);
    }



}
