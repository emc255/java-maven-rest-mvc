package app.service;

import app.model.VolcanoDTO;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class VolcanoCSVImpl implements VolcanoCSV {
    @Override
    public List<VolcanoDTO> convertCSV(File csvFile) {
        try {
            return new CsvToBeanBuilder<VolcanoDTO>(new FileReader(csvFile))
                    .withType(VolcanoDTO.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException w) {
            throw new RuntimeException();
        }

    }
}
