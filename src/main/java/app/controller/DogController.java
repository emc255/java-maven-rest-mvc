package app.controller;

import app.exception.NotFoundException;
import app.model.Dog;
import app.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DogController {

    public static final String DOG_PATH = "/api/v1/dog";
    public static final String DOG_PATH_ID = DOG_PATH + "/{id}";
    public static final String DOG_PATH_ADD = DOG_PATH + "/add";
    private final DogService dogService;

    @GetMapping({DOG_PATH, DOG_PATH + "/"})
    public List<Dog> beerList() {
        return dogService.dogList();
    }

    @GetMapping(DOG_PATH_ID)
    public Dog getDogById(@PathVariable("id") UUID id) {
        return dogService.getDogById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(DOG_PATH_ADD)
    public ResponseEntity<Dog> addDog(@RequestBody Dog dog) {
        Dog savedDog = dogService.addDog(dog);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", DOG_PATH + "/" + savedDog.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(DOG_PATH_ID)
    public ResponseEntity<Dog> updateDogById(@PathVariable("id") UUID id, @RequestBody Dog dog) {
        dogService.updateDogById(id, dog);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(DOG_PATH_ID)
    public ResponseEntity<Dog> patchDogById(@PathVariable("id") UUID id, @RequestBody Dog dog) {
        dogService.patchDogById(id, dog);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DOG_PATH_ID)
    public ResponseEntity<Dog> deleteDogById(@PathVariable("id") UUID id) {
        dogService.deleteDogById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
