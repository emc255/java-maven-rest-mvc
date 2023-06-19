package app.repository;

import app.entity.Category;
import app.entity.Customer;
import app.entity.Dog;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    DogRepository dogRepository;
    @Autowired
    CategoryRepository categoryRepository;

    Customer customer;
    Dog dog;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        dog = dogRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testAddCategory_WhenAllFieldsAreValid_ExpectedSizeEquals1() {
        Category category = Category.builder()
                .description("describe")
                .build();
        categoryRepository.save(category);
        dog.addCategory(category);
        Dog saveDog = dogRepository.save(dog);
        System.out.println(saveDog);
    }
}