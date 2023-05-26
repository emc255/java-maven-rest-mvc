package app.controller;

import app.model.Customer;
import app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/list")
    public List<Customer> customerList() {
        return customerService.customerList();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer/" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        Customer updateCustomer = customerService.updateCustomerById(id, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer" + updateCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
