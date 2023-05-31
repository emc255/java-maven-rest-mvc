package app.controller;

import app.exception.NotFoundException;
import app.model.DogDTO;
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
    public List<DogDTO> dogList() {
        return dogService.dogList();
    }

    @GetMapping(DOG_PATH_ID)
    public DogDTO getDogById(@PathVariable("id") UUID id) {
        return dogService.getDogById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(DOG_PATH_ADD)
    public ResponseEntity<DogDTO> addDog(@RequestBody DogDTO dogDTO) {
        DogDTO savedDog = dogService.addDog(dogDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", DOG_PATH + "/" + savedDog.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(DOG_PATH_ID)
    public ResponseEntity<DogDTO> updateDogById(@PathVariable("id") UUID id, @RequestBody DogDTO dog) {
        if (dogService.updateDogById(id, dog).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DOG_PATH_ID)
    public ResponseEntity<DogDTO> deleteDogById(@PathVariable("id") UUID id) {
        if (dogService.deleteDogById(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        throw new NotFoundException();
    }

    @PatchMapping(DOG_PATH_ID)
    public ResponseEntity<DogDTO> patchDogById(@PathVariable("id") UUID id, @RequestBody DogDTO dogDTO) {
        if (dogService.patchDogById(id, dogDTO).isEmpty()) throw new NotFoundException();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
