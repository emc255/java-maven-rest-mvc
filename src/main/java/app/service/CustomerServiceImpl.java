package app.service;

import app.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, CustomerDTO> data = new HashMap<>();

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("AI")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("IU")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Jessica")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        data.put(customer1.getId(), customer1);
        data.put(customer2.getId(), customer2);
        data.put(customer3.getId(), customer3);
    }

    @Override
    public List<CustomerDTO> customerList() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(data.get(id));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customerDTO.getName())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        data.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = data.get(id);
        updatedCustomer.setName(customerDTO.getName());
        updatedCustomer.setUpdateDate(LocalDateTime.now());
        return Optional.of(updatedCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        data.remove(id);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = data.get(id);
        if (customerDTO.getName() != null) updatedCustomer.setName(customerDTO.getName());
        updatedCustomer.setUpdateDate(LocalDateTime.now());
        return Optional.of(updatedCustomer);
    }
}
