package app.repository;

import app.entity.Volcano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VolcanoRepository extends JpaRepository<Volcano, UUID> {
    List<Volcano> findAllByCountryIsLikeIgnoreCase(String country);

    List<Volcano> findAllByRegionIsLikeIgnoreCase(String region);

    List<Volcano> findAllByCountryIsLikeIgnoreCaseAndRegionIsLikeIgnoreCase(String country, String region);
}