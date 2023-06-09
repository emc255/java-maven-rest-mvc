package app.controller;

import app.mapper.VolcanoMapper;
import app.model.VolcanoDTO;
import app.repository.VolcanoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class VolcanoControllerTest {
    @Autowired
    VolcanoController volcanoController;
    @Autowired
    VolcanoRepository volcanoRepository;
    @Autowired
    VolcanoMapper mapper;

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
    void testVolcanoList() {
        List<VolcanoDTO> volcanoDTOList = volcanoController.volcanoList(null, null);
        assertThat(volcanoDTOList.size()).isEqualTo(1571);
    }

    @Test
    void testVolcanoListByCountryAndRegion_WhenParamsNotNull_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("country", "Guatemala")
                        .queryParam("region", "Guatemala"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(22)));
    }

    @Test
    void testVolcanoListByCountry_WhenParamsNotNull_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("country", "States"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(184)));
    }

    @Test
    void testVolcanoListByRegion_WhenParamsNotNull_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("region", "Cape"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)));
    }


}