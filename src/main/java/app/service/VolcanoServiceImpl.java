package app.service;

import app.entity.Volcano;
import app.mapper.VolcanoMapper;
import app.model.VolcanoDTO;
import app.repository.VolcanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class VolcanoServiceImpl implements VolcanoService {

    private final VolcanoRepository volcanoRepository;
    private final VolcanoMapper volcanoMapper;

    @Override
    public List<VolcanoDTO> volcanoList() {
        return volcanoRepository.findAll()
                .stream()
                .map(volcanoMapper::convertVolcanoToVolcanoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VolcanoDTO> getVolcanoById(UUID id) {
        Volcano volcano = volcanoRepository.findById(id).orElse(null);
        VolcanoDTO volcanoDTO = volcanoMapper.convertVolcanoToVolcanoDTO(volcano);
        return Optional.ofNullable(volcanoDTO);
    }

    @Override
    public VolcanoDTO addVolcano(VolcanoDTO volcanoDTO) {
        Volcano volcano = volcanoMapper.convertVolcanoDTOToVolcano(volcanoDTO);
        Volcano saveVolcano = volcanoRepository.save(volcano);
        return volcanoMapper.convertVolcanoToVolcanoDTO(saveVolcano);
    }

    @Override
    public Optional<VolcanoDTO> updateVolcanoById(UUID id, VolcanoDTO volcanoDTO) {
        AtomicReference<Optional<VolcanoDTO>> atomicReference = new AtomicReference<>();
        volcanoRepository.findById(id).ifPresentOrElse(updateVolcano -> {
            updateVolcano.setName(volcanoDTO.getName());
            volcanoRepository.save(updateVolcano);
            VolcanoDTO updateVolcanoDTO = volcanoMapper.convertVolcanoToVolcanoDTO(updateVolcano);
            atomicReference.set(Optional.of(updateVolcanoDTO));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteVolcanoById(UUID id) {
        if (volcanoRepository.existsById(id)) {
            volcanoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
