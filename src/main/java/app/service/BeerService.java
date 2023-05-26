package app.service;

import app.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> beerList();

    Beer getBeerById(UUID id);

    Beer addBeer(Beer beer);

    void updateBeerById(UUID id, Beer beer);
}
