package app.service;

import app.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> customerList();

    Customer getCustomerById(UUID id);

    Customer addCustomer(Customer customer);

    Customer updateCustomerById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}
