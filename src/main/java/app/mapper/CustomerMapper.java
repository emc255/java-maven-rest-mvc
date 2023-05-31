package app.mapper;

import app.entity.Customer;
import app.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO);

    CustomerDTO convertCustomerToCustomerDTO(Customer customer);
}
