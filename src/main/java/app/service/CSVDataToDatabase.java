package app.service;

import app.model.VolcanoDTO;
import app.model_csv.DogCSV;
import app.model_csv.EarthquakeCSV;

import java.io.File;
import java.util.List;

public interface CSVDataToDatabase {
    List<VolcanoDTO> volcanoCSV(File csvFile);

    List<EarthquakeCSV> earthquakeCSV(File csvFile);

    List<DogCSV> dogNames(File csvFile);
}
