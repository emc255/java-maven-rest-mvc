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
        CustomerDTO testCustomerDTO = customerServiceImpl.customerList().get(0);
        given(customerService.getCustomerById(testCustomerDTO.getId())).willReturn(Optional.of(testCustomerDTO));
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(testCustomerDTO.getName())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.customerList().get(0);
        testCustomerDTO.setId(null);
        testCustomerDTO.setVersion(null);
    
        given(customerService.addCustomer(customerArgumentCaptor.capture())).willReturn(customerServiceImpl.customerList().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testAddCustomerFieldNameNull() throws Exception {
        CustomerDTO testCustomerDTO = CustomerDTO.builder().build();
        given(customerService.addCustomer(testCustomerDTO)).willReturn(null);

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)));

        /*
         * Useful method/trick to see the result
         * Use debugger
         * */

        MvcResult mvcResult = mockMvc.perform(post(CustomerController.CUSTOMER_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.customerList().get(0);
        testCustomerDTO.setName("AII");
        given(customerService.updateCustomerById(testCustomerDTO.getId(), testCustomerDTO)).willReturn(Optional.of(testCustomerDTO));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"));

        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateCustomerByIdFieldNameNull() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.customerList().get(0);
        testCustomerDTO.setName(null);
        given(customerService.updateCustomerById(testCustomerDTO.getId(), testCustomerDTO)).willReturn(Optional.of(testCustomerDTO));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)));


    }

    @Test
    void testDeleteCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.customerList().get(0);
        given(customerService.deleteCustomerById(any())).willReturn(true);
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.customerList().get(0);
        testCustomerDTO.setName("AIAIAI");


        given(customerService.patchCustomerById(testCustomerDTO.getId(), testCustomerDTO)).willReturn(Optional.of(testCustomerDTO));
        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(testCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(testCustomerDTO.getName()).isEqualTo(customerArgumentCaptor.getValue().getName());
    }

}