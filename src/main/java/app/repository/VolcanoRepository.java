package app.repository;

import app.entity.Volcano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VolcanoRepository extends JpaRepository<Volcano, UUID> {
    Page<Volcano> findAllByCountryIsLikeIgnoreCase(String country, Pageable pageable);

    Page<Volcano> findAllByRegionIsLikeIgnoreCase(String region, Pageable pageable);

    Page<Volcano> findAllByCountryIsLikeIgnoreCaseAndRegionIsLikeIgnoreCase(String country, String region, Pageable pageable);
}