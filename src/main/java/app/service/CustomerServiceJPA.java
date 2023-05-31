package app.service;

import app.entity.Customer;
import app.mapper.CustomerMapper;
import app.model.CustomerDTO;
import app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> customerList() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        Customer savedCustomer = customerRepository.findById(id).orElse(null);
        CustomerDTO saveCustomerDTO = customerMapper.convertCustomerToCustomerDTO(savedCustomer);
        return Optional.ofNullable(saveCustomerDTO);
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.convertCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.convertCustomerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customerDTO) {
        customerRepository.findById(id).ifPresent(updateCustomer -> {
            updateCustomer.setName(customerDTO.getName());
            customerRepository.save(updateCustomer);
        });
        Customer updateCustomer = customerRepository.findById(id).orElse(null);
        return customerMapper.convertCustomerToCustomerDTO(updateCustomer);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customerDTO) {
        customerRepository.findById(id).ifPresent(patchCustomer -> {
            if (customerDTO.getName() != null) patchCustomer.setName(customerDTO.getName());
            patchCustomer.setLastModifiedDate(LocalDateTime.now());
            customerRepository.save(patchCustomer);
        });
    }
}
