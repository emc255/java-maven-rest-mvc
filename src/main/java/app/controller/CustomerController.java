package app.controller;

import app.exception.NotFoundException;
import app.model.Beer;
import app.model.Customer;
import app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";
    public static final String CUSTOMER_PATH_ADD = CUSTOMER_PATH + "/add";
    private final CustomerService customerService;

    @GetMapping({CUSTOMER_PATH, CUSTOMER_PATH + "/"})
    public List<Customer> customerList() {
        return customerService.customerList();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH_ADD)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer/" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        Customer updateCustomer = customerService.updateCustomerById(id, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer/" + updateCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Beer> patchCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        customerService.patchCustomerById(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Beer> deleteCustomerById(@PathVariable("id") UUID id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
