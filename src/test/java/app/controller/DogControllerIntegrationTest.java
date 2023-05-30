package app.controller;

import app.entity.Dog;
import app.exception.NotFoundException;
import app.model.DogDTO;
import app.repository.DogRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DogControllerIntegrationTest {
    @Autowired
    DogController dogController;
    @Autowired
    DogRepository dogRepository;

    @Test
    void testDogList() throws Exception {
        List<DogDTO> testDogList = dogController.dogList();
        assertThat(testDogList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList() {
        dogRepository.deleteAll();
        List<DogDTO> testDogList = dogController.dogList();
        assertThat(testDogList.size()).isEqualTo(0);
    }

    @Test
    void getDogById() throws Exception {
        Dog testDog = dogRepository.findAll().get(0);
        DogDTO testDogDTO = dogController.getDogById(testDog.getId());
        assertThat(testDogDTO).isNotNull();
    }

    @Test
    void testDogIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            dogController.getDogById(UUID.randomUUID());
        });
    }
}