package app.service;

import app.model.DogBreed;
import app.model.DogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class DogServiceImpl implements DogService {
    private final Map<UUID, DogDTO> data;

    public DogServiceImpl() {
        this.data = new HashMap<>();

        DogDTO dog1 = DogDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Whitey")
                .dogBreed(DogBreed.ALASKAN_MALAMUTE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        DogDTO dog2 = DogDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Blacky")
                .dogBreed(DogBreed.SIBERIAN_HUSKY)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        DogDTO dog3 = DogDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Browny")
                .dogBreed(DogBreed.GOLDEN_RETRIEVER)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        data.put(dog1.getId(), dog1);
        data.put(dog2.getId(), dog2);
        data.put(dog3.getId(), dog3);
    }

    @Override
    public List<DogDTO> dogList() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<DogDTO> getDogById(UUID id) {

        log.debug("Get Beer by Id - in service. Id: " + id.toString());

        return Optional.of(data.get(id));
    }

    @Override
    public DogDTO addDog(DogDTO dog) {
        DogDTO savedDog = DogDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name(dog.getName())
                .dogBreed(dog.getDogBreed())
                .upc(dog.getUpc())
                .price(dog.getPrice())
                .quantityOnHand(dog.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        data.put(savedDog.getId(), savedDog);

        return savedDog;
    }

    @Override
    public void updateDogById(UUID id, DogDTO dog) {
        DogDTO updateDod = data.get(id);
        updateDod.setName(dog.getName());
        updateDod.setDogBreed(dog.getDogBreed());
        updateDod.setQuantityOnHand(dog.getQuantityOnHand());
        updateDod.setPrice(dog.getPrice());
        updateDod.setUpdateDate(LocalDateTime.now());
    }

    @Override
    public void patchDogById(UUID id, DogDTO dog) {
        DogDTO updateDod = data.get(id);
        if (dog.getName() != null) updateDod.setName(dog.getName());
        if (dog.getDogBreed() != null) updateDod.setDogBreed(dog.getDogBreed());
        if (dog.getQuantityOnHand() != null) updateDod.setQuantityOnHand(dog.getQuantityOnHand());
        if (dog.getPrice() != null) updateDod.setPrice(dog.getPrice());
        updateDod.setUpdateDate(LocalDateTime.now());
    }

    @Override
    public void deleteDogById(UUID id) {
        data.remove(id);
    }
}
