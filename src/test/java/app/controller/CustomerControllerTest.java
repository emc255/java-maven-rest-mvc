package app.controller;

import app.model.CustomerDTO;
import app.service.CustomerService;
import app.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;
    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testCustomerList() throws Exception {
        List<CustomerDTO> testCustomerList = customerServiceImpl.customerList();
        given(customerService.customerList()).willReturn(testCustomerList);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));

    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.customerList().get(0);
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.customerList().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.addCustomer(customerArgumentCaptor.capture())).willReturn(customerServiceImpl.customerList().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testAddCustomerWithNullName() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().build();
        given(customerService.addCustomer(customerDTO)).willReturn(null);

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isBadRequest());

        /*
         * Useful method/trick to see the result
         * Use debugger
         * */

        MvcResult mvcResult = mockMvc.perform(post(CustomerController.CUSTOMER_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.customerList().get(0);
        testCustomer.setName("AII");
        given(customerService.updateCustomerById(testCustomer.getId(), testCustomer)).willReturn(Optional.of(testCustomer));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"));

        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.customerList().get(0);
        given(customerService.deleteCustomerById(any())).willReturn(true);
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.customerList().get(0);
        testCustomer.setName("AIAIAI");


        given(customerService.patchCustomerById(testCustomer.getId(), testCustomer)).willReturn(Optional.of(testCustomer));
        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(testCustomer.getName()).isEqualTo(customerArgumentCaptor.getValue().getName());
    }

}