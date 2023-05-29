package app.service;

import app.model.Dog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DogService {
    List<Dog> dogList();

    Optional<Dog> getDogById(UUID id);

    Dog addDog(Dog beer);

    void updateDogById(UUID id, Dog beer);

    void deleteDogById(UUID id);

    void patchDogById(UUID id, Dog beer);
}
