package app.service;

import app.entity.Dog;
import app.mapper.DogMapper;
import app.model.DogDTO;
import app.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class DogServiceJPA implements DogService {
    private final DogRepository dogRepository;
    private DogMapper dogMapper;

    @Override
    public List<DogDTO> dogList() {
        return dogRepository.findAll()
                .stream()
                .map(dogMapper::convertDogToDogDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DogDTO> getDogById(UUID id) {
        return Optional.ofNullable(dogMapper.convertDogToDogDTO(dogRepository.findById(id).orElse(null)));
    }

    @Override
    public DogDTO addDog(DogDTO dog) {
        Dog saveDog = dogMapper.convertCustomerDTOToDog(dog);
        dogRepository.save(saveDog);
        return null;

    }

    @Override
    public void updateDogById(UUID id, DogDTO dog) {

    }

    @Override
    public void deleteDogById(UUID id) {

    }

    @Override
    public void patchDogById(UUID id, DogDTO dog) {

    }
}
