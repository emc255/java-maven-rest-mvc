package app.service;

import app.model.EarthquakeCSV;
import app.model.VolcanoDTO;

import java.io.File;
import java.util.List;

public interface CSVDataToDatabase {
    List<VolcanoDTO> volcanoCSV(File csvFile);

    List<EarthquakeCSV> earthquakeCSV(File csvFile);
}
