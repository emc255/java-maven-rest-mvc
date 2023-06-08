package app.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolcanoDTO {

    private UUID id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String country;

    @CsvBindByName
    private String region;

    @CsvBindByName
    private Double latitude;

    @CsvBindByName
    private Double longitude;

    @CsvBindByName
    private Integer elevation;

    @CsvBindByName
    private String type;

    @CsvBindByName
    private String status;
}
