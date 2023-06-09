package app.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolcanoDTO {

    private UUID id;

    @CsvBindByName
    @NotNull
    @NotBlank
    private String name;

    @CsvBindByName
    @NotNull
    @NotBlank
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

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
