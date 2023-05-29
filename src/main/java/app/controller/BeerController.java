package app.controller;

import app.model.Beer;
import app.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";
    public static final String BEER_PATH_ADD = BEER_PATH + "/add";
    private final BeerService beerService;

    @GetMapping({BEER_PATH, BEER_PATH + "/"})
    public List<Beer> beerList() {
        return beerService.beerList();
    }

    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("id") UUID id) {
        return beerService.getBeerById(id);
    }

    @PostMapping(BEER_PATH_ADD)
    public ResponseEntity<Beer> addBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> updateBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        beerService.updateBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> patchBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        beerService.patchBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> deleteBeerById(@PathVariable("id") UUID id) {
        beerService.deleteBeerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
