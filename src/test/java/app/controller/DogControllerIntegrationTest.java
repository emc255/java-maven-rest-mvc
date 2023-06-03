package app.controller;

import app.entity.Dog;
import app.exception.NotFoundException;
import app.mapper.DogMapper;
import app.model.DogBreed;
import app.model.DogDTO;
import app.repository.DogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DogControllerIntegrationTest {
    @Autowired
    DogController dogController;
    @Autowired
    DogRepository dogRepository;
    @Autowired
    DogMapper dogMapper;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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
        ResponseEntity<Void> responseEntity = dogController.addDog(dogDTO);


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

        ResponseEntity<Void> responseEntity = dogController.updateDogById(dog.getId(), dogDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Dog testUpdateDog = dogRepository.findById(dog.getId()).orElse(null);
        assertThat(testUpdateDog).isNotNull();
        assertThat(testUpdateDog.getName()).isEqualTo("Sukoshi");
        assertThat(testUpdateDog.getDogBreed()).isEqualTo(DogBreed.SHIBA_INU);
    }

    @Test
    void testUpdateDogByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            dogController.updateDogById(UUID.randomUUID(), DogDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteDogById() {
        UUID dogId = dogRepository.findAll().get(0).getId();
        ResponseEntity<Void> responseEntity = dogController.deleteDogById(dogId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(dogRepository.findById(dogId)).isEmpty();
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList.size()).isEqualTo(2);
    }

    @Test
    void testDeleteDogByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            dogController.deleteDogById(UUID.randomUUID());
        });
    }

    @Test
    void testPatchDogById() throws Exception {
        UUID dogId = dogRepository.findAll().get(0).getId();
        Dog dog = Dog.builder()
                .name("Cupid")
                .dogBreed(DogBreed.GOLDEN_RETRIEVER)
                .upc("1221")
                .price(new BigDecimal("11.22"))
                .build();
        DogDTO dogDTO = dogMapper.convertDogToDogDTO(dog);

        ResponseEntity<Void> responseEntity = dogController.patchDogById(dogId, dogDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Dog testUpdateDog = dogRepository.findById(dogId).orElse(null);
        assertThat(testUpdateDog).isNotNull();
        assertThat(testUpdateDog.getName()).isEqualTo(dog.getName());
        assertThat(testUpdateDog.getPrice()).isEqualTo(dog.getPrice());
    }

    @Test
    void testPatchDogByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            dogController.patchDogById(UUID.randomUUID(), DogDTO.builder().build());
        });
    }

    @Test
    void testPatchDogByIdNameExceed50() throws Exception {
        UUID dogId = dogRepository.findAll().get(0).getId();
        Dog dog = Dog.builder()
                .name("123456789101234567891012345678910123456789101234567891012345678910")
                .dogBreed(DogBreed.GOLDEN_RETRIEVER)
                .upc("1221")
                .price(new BigDecimal("11.22"))
                .build();
        DogDTO dogDTO = dogMapper.convertDogToDogDTO(dog);

        MvcResult mvcResult = mockMvc.perform(patch(DogController.DOG_PATH_ID, dogId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dogDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

    }
}