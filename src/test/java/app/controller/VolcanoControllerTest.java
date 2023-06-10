package app.controller;

import app.mapper.VolcanoMapper;
import app.repository.VolcanoRepository;
import app.service.VolcanoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
class VolcanoControllerTest {
    @Autowired
    VolcanoController volcanoController;
    @Autowired
    VolcanoRepository volcanoRepository;
    @Autowired
    VolcanoService volcanoService;
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
    void testVolcanoList() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(25)));

    }

    @Test
    void testVolcanoList_WhenPageSizeIsGreaterThan1000_ExpectedSize1000() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("pageSize", "2000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(1000)));

    }

    @Test
    void testVolcanoListByCountryAndRegion_WhenParamsNotNull_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("country", "Guatemala")
                        .queryParam("region", "Guatemala"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(22)));
    }

    @Test
    void testVolcanoListByCountry_WhenParamsNotNull_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("country", "States"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(25)));
    }

    @Test
    void testVolcanoListByRegion_WhenParamsNotNull_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("region", "Cape"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(3)));
    }

    @Test
    void testVolcanoListByCountryAndRegion_WhenPageNumberIsGreaterThan1_ExpectedSizeNotEmpty() throws Exception {
        mockMvc.perform(get(VolcanoController.VOLCANO_PATH)
                        .queryParam("country", "States")
                        .queryParam("region", "")
                        .queryParam("pageNumber", "2")
                        .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(50)));
    }


}