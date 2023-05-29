package app.controller;

import app.model.Dog;
import app.service.DogService;
import app.service.DogServiceImpl;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DogController.class)
class DogControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DogService dogService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<Dog> dogArgumentCaptor;

    DogServiceImpl dogServiceImpl;

    @BeforeEach
    void setUp() {
        dogServiceImpl = new DogServiceImpl();
    }

    @Test
    void dogList() throws Exception {
        List<Dog> testDogList = dogServiceImpl.dogList();
        given(dogService.dogList()).willReturn(testDogList);

        mockMvc.perform(get(DogController.DOG_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getDogById() throws Exception {
        Dog testDog = dogServiceImpl.dogList().get(0);
        given(dogService.getDogById(testDog.getId()))
                .willReturn(Optional.of(testDog));

        mockMvc.perform(get(DogController.DOG_PATH_ID, testDog.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testDog.getId().toString())))
                .andExpect(jsonPath("$.name", is(testDog.getName())));
    }


    @Test
    public void addDog() throws Exception {
        Dog dog = dogServiceImpl.dogList().get(0);
        dog.setId(null);
        dog.setVersion(null);

        given(dogService.addDog(dogArgumentCaptor.capture())).willReturn(dogServiceImpl.dogList().get(1));

        mockMvc.perform(post(DogController.DOG_PATH_ADD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void updateDogById() throws Exception {
        Dog testDog = dogServiceImpl.dogList().get(0);

        mockMvc.perform(put(DogController.DOG_PATH_ID, testDog.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDog)))
                .andExpect(status().isNoContent());
        verify(dogService).updateDogById(uuidArgumentCaptor.capture(), dogArgumentCaptor.capture());
        assertThat(testDog.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(testDog).isEqualTo(dogArgumentCaptor.getValue());
    }

    @Test
    void patchDogById() throws Exception {
        Dog testDog = dogServiceImpl.dogList().get(0);
        Map<String, Object> dogMap = new HashMap<>();
        dogMap.put("name", "new Name");
        mockMvc.perform(patch(DogController.DOG_PATH_ID, testDog.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dogMap)))
                .andExpect(status().isNoContent());

        verify(dogService).patchDogById(uuidArgumentCaptor.capture(), dogArgumentCaptor.capture());
        assertThat(testDog.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(dogMap.get("name")).isEqualTo(dogArgumentCaptor.getValue().getName());
    }

    @Test
    void deleteDogById() throws Exception {
        Dog testDog = dogServiceImpl.dogList().get(0);

        mockMvc.perform(delete(DogController.DOG_PATH_ID, testDog.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(dogService).deleteDogById(uuidArgumentCaptor.capture());
        assertThat(testDog.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getDogByIdNotFound() throws Exception {
        given(dogService.getDogById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(DogController.DOG_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}