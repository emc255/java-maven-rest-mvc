package app.service;

import app.entity.Dog;
import app.mapper.DogMapper;
import app.model.DogDTO;
import app.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class DogServiceJPA implements DogService {
    private final DogRepository dogRepository;
    private final DogMapper dogMapper;

    @Override
    public List<DogDTO> dogList() {
        return dogRepository.findAll()
                .stream()
                .map(dogMapper::convertDogToDogDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DogDTO> getDogById(UUID id) {
        Dog saveDog = dogRepository.findById(id).orElse(null);
        DogDTO saveDogDTO = dogMapper.convertDogToDogDTO(saveDog);
        return Optional.ofNullable(saveDogDTO);
    }

    @Override
    public DogDTO addDog(DogDTO dogDTO) {
        Dog dog = dogMapper.convertDogDTOToDog(dogDTO);
        Dog saveDog = dogRepository.save(dog);
        return dogMapper.convertDogToDogDTO(saveDog);
    }

    @Override
    public Optional<DogDTO> updateDogById(UUID id, DogDTO dogDTO) {
        AtomicReference<Dog> atomicReference = new AtomicReference<>();
        dogRepository.findById(id).ifPresent(updateDog -> {
            updateDog.setName(dogDTO.getName());
            updateDog.setDogBreed(dogDTO.getDogBreed());
            updateDog.setQuantityOnHand(dogDTO.getQuantityOnHand());
            updateDog.setPrice(dogDTO.getPrice());
            updateDog.setUpdateDate(LocalDateTime.now());
            
            atomicReference.set(dogRepository.save(updateDog));
        });

        return Optional.of(dogMapper.convertDogToDogDTO(atomicReference.get()));
    }

    @Override
    public void deleteDogById(UUID id) {

    }

    @Override
    public void patchDogById(UUID id, DogDTO dogDTO) {

    }
}
