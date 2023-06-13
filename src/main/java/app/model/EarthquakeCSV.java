package app.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarthquakeCSV {
    private UUID id;

    @CsvBindByName(column = "date")
    private String eruptionDate;

    @CsvBindByName
    private Double latitude;

    @CsvBindByName
    private Double longitude;

    @CsvBindByName
    private Double magnitude;
}
