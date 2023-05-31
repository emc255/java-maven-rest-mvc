package app.controller;

import app.entity.Dog;
import app.exception.NotFoundException;
import app.mapper.DogMapper;
import app.model.DogBreed;
import app.model.DogDTO;
import app.repository.DogRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
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
    @Autowired
    DogMapper dogMapper;

    @Test
    void testDogList() throws Exception {
        List<DogDTO> testDogList = dogController.dogList();
        assertThat(testDogList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testDogListEmpty() {
        dogRepository.deleteAll();
        List<DogDTO> testDogList = dogController.dogList();
        assertThat(testDogList.size()).isEqualTo(0);
    }

    @Test
    void testGetDogById() throws Exception {
        Dog testDog = dogRepository.findAll().get(0);
        DogDTO testDogDTO = dogController.getDogById(testDog.getId());
        assertThat(testDogDTO).isNotNull();
    }

    @Test
    void testGetDogByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            dogController.getDogById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testAddDog() {
        DogDTO dogDTO = DogDTO.builder()
                .name("pika")
                .dogBreed(DogBreed.GOLDEN_RETRIEVER)
                .price(new BigDecimal("11.11"))
                .build();
        ResponseEntity<DogDTO> responseEntity = dogController.addDog(dogDTO);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUIID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID dogDTOId = UUID.fromString(locationUIID[4]);

        Dog testDog = dogRepository.findById(dogDTOId).orElse(null);
        assertThat(testDog).isNotNull();
    }

    @Test
    void testUpdateDogById() {
        Dog dog = dogRepository.findAll().get(0);
        DogDTO dogDTO = dogMapper.convertDogToDogDTO(dog);
        dogDTO.setId(null);
        dogDTO.setVersion(null);
        dogDTO.setName("Sukoshi");
        dogDTO.setDogBreed(DogBreed.SHIBA_INU);

        ResponseEntity<DogDTO> responseEntity = dogController.updateDogById(dog.getId(), dogDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Dog testUpdateDog = dogRepository.findById(dog.getId()).orElse(null);
        assertThat(testUpdateDog).isNotNull();
        assertThat(testUpdateDog.getName()).isEqualTo("Sukoshi");
        assertThat(testUpdateDog.getDogBreed()).isEqualTo(DogBreed.SHIBA_INU);
    }

    @Test
    void testUpdateDogByIdNotFound() {

    }
}