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
        dog.setCreatedDate(LocalDateTime.now());
        dog.setUpdateDate(LocalDateTime.now());
        Dog saveDog = dogRepository.save(dog);
        return dogMapper.convertDogToDogDTO(saveDog);
    }

    @Override
    public Optional<DogDTO> updateDogById(UUID id, DogDTO dogDTO) {
        AtomicReference<Optional<DogDTO>> atomicReference = new AtomicReference<>();
        dogRepository.findById(id).ifPresentOrElse(updateDog -> {
            updateDog.setName(dogDTO.getName());
            updateDog.setBreed(dogDTO.getBreed());
            updateDog.setUpc(dogDTO.getUpc());
            updateDog.setQuantityOnHand(dogDTO.getQuantityOnHand());
            updateDog.setPrice(dogDTO.getPrice());
            updateDog.setUpdateDate(LocalDateTime.now());
            dogRepository.save(updateDog);
            DogDTO updateDogDTO = dogMapper.convertDogToDogDTO(updateDog);
            atomicReference.set(Optional.of(updateDogDTO));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteDogById(UUID id) {
        if (dogRepository.existsById(id)) {
            dogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<DogDTO> patchDogById(UUID id, DogDTO dogDTO) {
        AtomicReference<Optional<DogDTO>> atomicReference = new AtomicReference<>();
        dogRepository.findById(id).ifPresentOrElse(updateDog -> {
            Optional.of(dogDTO.getName()).ifPresent(updateDog::setName);
            Optional.of(dogDTO.getBreed()).ifPresent(updateDog::setBreed);
            Optional.of(dogDTO.getUpc()).ifPresent(updateDog::setUpc);
            Optional.ofNullable(dogDTO.getQuantityOnHand()).ifPresent(updateDog::setQuantityOnHand);
            Optional.of(dogDTO.getPrice()).ifPresent(updateDog::setPrice);
            updateDog.setUpdateDate(LocalDateTime.now());
            dogRepository.save(updateDog);
            DogDTO updateDogDTO = dogMapper.convertDogToDogDTO(updateDog);
            atomicReference.set(Optional.of(updateDogDTO));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
