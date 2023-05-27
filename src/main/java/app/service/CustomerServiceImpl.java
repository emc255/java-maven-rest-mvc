package app.service;

import app.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service

public class CustomerServiceImpl implements CustomerService {


    private final Map<UUID, Customer> data = new HashMap<>();

    public CustomerServiceImpl() {
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("AI")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("IU")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Jessica")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        data.put(customer1.getId(), customer1);
        data.put(customer2.getId(), customer2);
        data.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> customerList() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return data.get(id);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        data.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Customer updateCustomerById(UUID id, Customer customer) {
        Customer updatedCustomer = data.get(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setLastModifiedDate(LocalDateTime.now());
        return updatedCustomer;
    }

    @Override
    public void patchCustomerById(UUID id, Customer customer) {
        Customer updatedCustomer = data.get(id);
        if (customer.getName() != null) updatedCustomer.setName(customer.getName());
        updatedCustomer.setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomerById(UUID id) {
        data.remove(id);
    }
}
