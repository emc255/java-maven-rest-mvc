package app.controller;

import app.model.Beer;
import app.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@AllArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping("/list")
    public List<Beer> beerList() {
        return beerService.beerList();
    }

    @GetMapping("/{id}")
    public Beer getBeerById(@PathVariable("id") UUID id) {
        return beerService.getBeerById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Beer> addBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beer> updateBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        beerService.updateBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
