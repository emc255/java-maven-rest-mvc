package app.mapper;

import app.entity.Volcano;
import app.model.VolcanoDTO;
import org.mapstruct.Mapper;

@Mapper
public interface VolcanoMapper {
    Volcano convertVolcanoDTOToVolcano(VolcanoDTO volcanoDTO);

    VolcanoDTO convertVolcanoToVolcanoDTO(Volcano volcano);
}
