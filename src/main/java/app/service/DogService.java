package app.service;

import app.model.DogDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DogService {
    List<DogDTO> dogList();

    Optional<DogDTO> getDogById(UUID id);

    DogDTO addDog(DogDTO dog);

    void updateDogById(UUID id, DogDTO dog);

    void deleteDogById(UUID id);

    void patchDogById(UUID id, DogDTO dog);
}
