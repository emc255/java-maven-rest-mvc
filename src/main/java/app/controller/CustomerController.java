package app.controller;

import app.exception.NotFoundException;
import app.model.CustomerDTO;
import app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public List<CustomerDTO> customerList() {
        return customerService.customerList();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH_ADD)
    public ResponseEntity<Void> addCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.addCustomer(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Void> updateCustomerById(@PathVariable("id") UUID id, @Validated @RequestBody CustomerDTO customerDTO) {
        Optional<CustomerDTO> updateCustomerDTO = customerService.updateCustomerById(id, customerDTO);
        if (updateCustomerDTO.isEmpty()) throw new NotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + updateCustomerDTO.get().getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.OK);

    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") UUID id) {
        if (customerService.deleteCustomerById(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        throw new NotFoundException();
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Void> patchCustomerById(@PathVariable("id") UUID id, @RequestBody CustomerDTO customerDTO) {
        if (customerService.patchCustomerById(id, customerDTO).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
