package app.service;

import app.model.VolcanoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VolcanoServiceImplTest {
    @Autowired
    VolcanoService volcanoService = new VolcanoServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csv-data/volcano_db.csv");
        List<VolcanoDTO> volcanoDTOList = volcanoService.convertCSV(file);
        System.out.println(volcanoDTOList.size());
        assertThat(volcanoDTOList.size()).isGreaterThan(0);
    }
}