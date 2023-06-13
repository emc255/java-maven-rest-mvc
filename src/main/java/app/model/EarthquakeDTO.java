package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarthquakeDTO {

    private UUID id;


    private LocalDateTime eruptionDate;

    private Double latitude;

    private Double longitude;

    private Double magnitude;
}
