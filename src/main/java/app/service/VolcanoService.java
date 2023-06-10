package app.service;

import app.model.VolcanoDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface VolcanoService {

    Page<VolcanoDTO> volcanoList(String country, String region, Integer pageNumber, Integer pageSize);

    Optional<VolcanoDTO> getVolcanoById(UUID id);

    VolcanoDTO addVolcano(VolcanoDTO dog);

    Optional<VolcanoDTO> updateVolcanoById(UUID id, VolcanoDTO dog);

    Boolean deleteVolcanoById(UUID id);

}
