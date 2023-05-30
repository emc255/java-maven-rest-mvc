package app.controller;

import app.entity.Customer;
import app.exception.NotFoundException;
import app.model.CustomerDTO;
import app.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    void testCustomerList() {
        List<CustomerDTO> testCustomerDTOList = customerController.customerList();
        assertThat(testCustomerDTOList.size()).isEqualTo(3);
    }

    @Test
    void testCustomerGetById() {
        Customer testCustomer = customerRepository.findAll().get(0);
        CustomerDTO testCustomerDTO = customerController.getCustomerById(testCustomer.getId());
        assertThat(testCustomerDTO).isNotNull();
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
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
}