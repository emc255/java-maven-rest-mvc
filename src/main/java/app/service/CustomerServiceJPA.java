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
import java.util.concurrent.atomic.AtomicReference;
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
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(updateCustomer -> {
            updateCustomer.setName(customerDTO.getName());
            customerRepository.save(updateCustomer);
            CustomerDTO updateCustomerDTO = customerMapper.convertCustomerToCustomerDTO(updateCustomer);
            atomicReference.set(Optional.of(updateCustomerDTO));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(updateCustomer -> {
            Optional.of(customerDTO.getName()).ifPresent(updateCustomer::setName);
            updateCustomer.setUpdateDate(LocalDateTime.now());
            customerRepository.save(updateCustomer);
            CustomerDTO updateCustomerDTO = customerMapper.convertCustomerToCustomerDTO(updateCustomer);
            atomicReference.set(Optional.of(updateCustomerDTO));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
