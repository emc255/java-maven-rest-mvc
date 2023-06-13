package app.mapper;

import app.entity.Earthquake;
import app.model.EarthquakeDTO;
import org.mapstruct.Mapper;

@Mapper
public interface EarthquakeMapper {
    Earthquake convertEarthquakeDTOToEarthquake(EarthquakeDTO earthquakeDTO);

    EarthquakeDTO convertEarthquakeToEarthquakeDTO(EarthquakeDTO earthquakeDTO);
}
