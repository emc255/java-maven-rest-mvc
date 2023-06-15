package app.repository;

import app.entity.Dog;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class DogRepositoryTest {
    @Autowired
    DogRepository dogRepository;

    @Test
    void testSaveDog() {
        Dog saveDog = dogRepository.save(Dog.builder()
                .name("Brute")
                .breed("Shiba Inu")
                .upc("21221")
                .quantityOnHand(12)
                .price(new BigDecimal("12.22"))
                .build());

        dogRepository.flush();

        assertThat(saveDog).isNotNull();
        assertThat(saveDog.getId()).isNotNull();

    }

    @Test
    void testSaveDogNameExceed50Char() {
        assertThrows(ConstraintViolationException.class, () -> {
            Dog saveDog = dogRepository.save(Dog.builder()
                    .name("123456789101234567891012345678910123456789101234567891012345678910")
                    .breed("Shiba Inu")
                    .upc("21221")
                    .quantityOnHand(12)
                    .price(new BigDecimal("12.22"))
                    .build());

            dogRepository.flush();
        });
    }
}