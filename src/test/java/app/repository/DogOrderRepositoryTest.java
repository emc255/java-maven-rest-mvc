package app.repository;

import app.entity.Customer;
import app.entity.Dog;
import app.entity.DogOrder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DogOrderRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    DogRepository dogRepository;
    @Autowired
    DogOrderRepository dogOrderRepository;

    Customer customer;
    Dog dog;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        dog = dogRepository.findAll().get(0);

    }

    @Test
    @Transactional
    void testDogOrder() {
        DogOrder dogOrder = DogOrder.builder()
                .customer(customer)
                .build();


        DogOrder saveDogOrder = dogOrderRepository.saveAndFlush(dogOrder);
        System.out.println(saveDogOrder);
        System.out.println(saveDogOrder.getCustomer().getName());
        assertThat(dogOrderRepository.count()).isEqualTo(1);

    }
}