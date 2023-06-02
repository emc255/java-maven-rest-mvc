package app.controller;

import app.entity.Customer;
import app.exception.NotFoundException;
import app.mapper.CustomerMapper;
import app.model.CustomerDTO;
import app.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testCustomerList() {
        List<CustomerDTO> testCustomerDTOList = customerController.customerList();
        assertThat(testCustomerDTOList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testCustomerListEmpty() {
        customerRepository.deleteAll();
        List<Customer> testCustomerList = customerRepository.findAll();
        assertThat(testCustomerList.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById() {
        Customer testCustomer = customerRepository.findAll().get(0);
        CustomerDTO testCustomerDTO = customerController.getCustomerById(testCustomer.getId());
        assertThat(testCustomerDTO).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testAddCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("Eva")
                .build();
        ResponseEntity<CustomerDTO> responseEntity = customerController.addCustomer(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID testCustomerDTOId = UUID.fromString(locationUUID[4]);

        Customer testCustomer = customerRepository.findById(testCustomerDTOId).orElse(null);
        assertThat(testCustomer).isNotNull();
    }

//    @Test
//    void testAddCustomerNullCustomerName() {
//        CustomerDTO customerDTO = CustomerDTO.builder()
//                .build();
//
//        ResponseEntity<CustomerDTO> responseEntity = customerController.addCustomer(customerDTO);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
//    }

    @Test
    void testUpdateCustomerById() {
        Customer testCustomer = customerRepository.findAll().get(0);
        CustomerDTO testCustomerDTO = customerMapper.convertCustomerToCustomerDTO(testCustomer);
        testCustomerDTO.setId(null);
        testCustomerDTO.setVersion(null);
        testCustomerDTO.setName("Jessica");

        ResponseEntity<CustomerDTO> responseEntity = customerController.updateCustomerById(testCustomer.getId(), testCustomerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        Customer testUpdateCustomer = customerRepository.findById(testCustomer.getId()).orElse(null);
        assertThat(testUpdateCustomer).isNotNull();
        assertThat(testUpdateCustomer.getName()).isEqualTo("Jessica");
    }

    @Test
    void testUpdateCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteCustomerById() {
        UUID customerId = customerRepository.findAll().get(0).getId();
        ResponseEntity<CustomerDTO> responseEntity = customerController.deleteCustomerById(customerId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customerId)).isEmpty();
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(2);
    }

    @Test
    void testDeleteCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testPatchCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO testCustomerDTO = customerMapper.convertCustomerToCustomerDTO(customer);
        testCustomerDTO.setId(null);
        testCustomerDTO.setVersion(null);
        testCustomerDTO.setName("Ikura");

        ResponseEntity<CustomerDTO> responseEntity = customerController.patchCustomerById(customer.getId(), testCustomerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer testCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertThat(testCustomer).isNotNull();
        assertThat(testCustomer.getName()).isEqualTo("Ikura");
    }

    @Test
    void testPatchCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.patchCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }
}