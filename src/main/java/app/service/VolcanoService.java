package app.service;

import app.model.VolcanoDTO;

import java.io.File;
import java.util.List;

public interface VolcanoService {
    List<VolcanoDTO> convertCSV(File csvFile);
}
