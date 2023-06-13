package app.service;

import app.entity.Volcano;
import app.mapper.VolcanoMapper;
import app.model.VolcanoDTO;
import app.repository.VolcanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Primary
@RequiredArgsConstructor
public class VolcanoServiceImpl implements VolcanoService {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    private final VolcanoRepository volcanoRepository;
    private final VolcanoMapper volcanoMapper;

    @Override
    public Page<VolcanoDTO> volcanoList(String country, String region, Integer pageNumber, Integer pageSize) {
        Page<Volcano> volcanoList;
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        if (StringUtils.hasText(country) && StringUtils.hasText(region)) {
            volcanoList = volcanoListByCountryAndRegion(country, region, pageRequest);
        } else if (StringUtils.hasText(country) && !StringUtils.hasText(region)) {
            volcanoList = volcanoListByCountry(country, pageRequest);
        } else if (!StringUtils.hasText(country) && StringUtils.hasText(region)) {
            volcanoList = volcanoListByRegion(region, pageRequest);
        } else {
            volcanoList = volcanoRepository.findAll(pageRequest);
        }

        return volcanoList.map(volcanoMapper::convertVolcanoToVolcanoDTO);
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

    private Page<Volcano> volcanoListByCountryAndRegion(String country, String region, Pageable pageable) {
        return volcanoRepository.findAllByCountryIsLikeIgnoreCaseAndRegionIsLikeIgnoreCase(addWildCard(country), addWildCard(region), pageable);
    }

    private Page<Volcano> volcanoListByCountry(String country, Pageable pageable) {
        return volcanoRepository.findAllByCountryIsLikeIgnoreCase(addWildCard(country), pageable);
    }

    private Page<Volcano> volcanoListByRegion(String region, Pageable pageable) {
        return volcanoRepository.findAllByRegionIsLikeIgnoreCase(addWildCard(region), pageable);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = pageNumber != null && pageNumber > 0 ? pageNumber - 1 : DEFAULT_PAGE_NUMBER;
        int queryPageSize = pageSize != null && pageSize > 0
                ? pageSize > 1000 ? 1000 : pageSize
                : DEFAULT_PAGE_SIZE;

        Sort sort = Sort.by(Sort.Order.asc("name"));
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    private String addWildCard(String word) {
        return "%" + word + "%";
    }
}
