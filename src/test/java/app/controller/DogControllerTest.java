package app.controller;

import app.model.DogDTO;
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
    ArgumentCaptor<DogDTO> dogArgumentCaptor;

    DogServiceImpl dogServiceImpl;

    @BeforeEach
    void setUp() {
        dogServiceImpl = new DogServiceImpl();
    }

    @Test
    void testDogList() throws Exception {
        List<DogDTO> testDogListDTO = dogServiceImpl.dogList();
        given(dogService.dogList()).willReturn(testDogListDTO);

        mockMvc.perform(get(DogController.DOG_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetDogById() throws Exception {
        DogDTO testDog = dogServiceImpl.dogList().get(0);
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
    void testGetDogByIdNotFound() throws Exception {
        given(dogService.getDogById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(DogController.DOG_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testAddDog() throws Exception {
        DogDTO dog = dogServiceImpl.dogList().get(0);
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
    public void testUpdateDogById() throws Exception {
        DogDTO testDog = dogServiceImpl.dogList().get(0);
        testDog.setName("Chocobo");
        given(dogService.updateDogById(testDog.getId(), testDog)).willReturn(Optional.of(testDog));

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
    void testDeleteDogById() throws Exception {
        DogDTO testDog = dogServiceImpl.dogList().get(0);
        given(dogService.deleteDogById(any())).willReturn(true);

        mockMvc.perform(delete(DogController.DOG_PATH_ID, testDog.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(dogService).deleteDogById(uuidArgumentCaptor.capture());
        assertThat(testDog.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchDogById() throws Exception {
        DogDTO testDog = dogServiceImpl.dogList().get(0);
        testDog.setName("New Name");

        given(dogService.patchDogById(testDog.getId(), testDog)).willReturn(Optional.of(testDog));
        mockMvc.perform(patch(DogController.DOG_PATH_ID, testDog.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDog)))
                .andExpect(status().isNoContent());

        verify(dogService).patchDogById(uuidArgumentCaptor.capture(), dogArgumentCaptor.capture());
        assertThat(testDog.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(testDog.getName()).isEqualTo(dogArgumentCaptor.getValue().getName());


    }


}