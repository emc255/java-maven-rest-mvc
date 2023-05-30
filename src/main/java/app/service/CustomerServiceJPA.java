package app.service;

import app.mapper.CustomerMapper;
import app.model.CustomerDTO;
import app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
        return Optional.ofNullable(customerMapper.convertCustomerToCustomerDTO(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customer) {
        return null;
    }

    @Override
    public void deleteCustomerById(UUID id) {

    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customer) {

    }
}
