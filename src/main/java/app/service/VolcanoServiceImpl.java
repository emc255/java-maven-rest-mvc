package app.service;

import app.entity.Volcano;
import app.mapper.VolcanoMapper;
import app.model.VolcanoDTO;
import app.repository.VolcanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@Primary
@RequiredArgsConstructor
public class VolcanoServiceImpl implements VolcanoService {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 0;

    private final VolcanoRepository volcanoRepository;
    private final VolcanoMapper volcanoMapper;

    @Override
    public List<VolcanoDTO> volcanoList(String country, String region, Integer pageNumber, Integer pageSize) {
        List<Volcano> volcanoList;
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        if (StringUtils.hasText(country) && StringUtils.hasText(region)) {
            volcanoList = volcanoListByCountryAndRegion(country, region);
        } else if (StringUtils.hasText(country) && !StringUtils.hasText(region)) {
            volcanoList = volcanoListByCountry(country);
        } else if (!StringUtils.hasText(country) && StringUtils.hasText(region)) {
            volcanoList = volcanoListByRegion(region);
        } else {
            volcanoList = volcanoRepository.findAll();
        }

        return volcanoList.stream()
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

    private List<Volcano> volcanoListByCountryAndRegion(String country, String region) {
        return volcanoRepository.findAllByCountryIsLikeIgnoreCaseAndRegionIsLikeIgnoreCase(addWildCard(country), addWildCard(region));
    }

    private List<Volcano> volcanoListByCountry(String country) {
        return volcanoRepository.findAllByCountryIsLikeIgnoreCase(addWildCard(country));
    }

    private List<Volcano> volcanoListByRegion(String region) {
        return volcanoRepository.findAllByRegionIsLikeIgnoreCase(addWildCard(region));
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = pageNumber != null && pageNumber > 0 ? pageNumber - 1 : DEFAULT_PAGE_NUMBER;
        int queryPageSize = pageSize != null && pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    private String addWildCard(String word) {
        return "%" + word + "%";
    }
}
