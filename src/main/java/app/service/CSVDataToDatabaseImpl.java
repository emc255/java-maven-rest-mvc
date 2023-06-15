package app.service;

import app.model.VolcanoDTO;
import app.model_csv.DogCSV;
import app.model_csv.EarthquakeCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CSVDataToDatabaseImpl implements CSVDataToDatabase {
    @Override
    public List<VolcanoDTO> volcanoCSV(File csvFile) {
        try {
            return new CsvToBeanBuilder<VolcanoDTO>(new FileReader(csvFile))
                    .withType(VolcanoDTO.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException w) {
            throw new RuntimeException();
        }

    }

    @Override
    public List<EarthquakeCSV> earthquakeCSV(File csvFile) {

        try {
            return new CsvToBeanBuilder<EarthquakeCSV>(new FileReader(csvFile))
                    .withType(EarthquakeCSV.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException w) {
            throw new RuntimeException();
        }

    }

    @Override
    public List<DogCSV> dogNames(File csvFile) {
        try {
            return new CsvToBeanBuilder<DogCSV>(new FileReader(csvFile))
                    .withType(DogCSV.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException w) {
            throw new RuntimeException();
        }
    }
}
