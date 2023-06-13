package app.repository;

import app.entity.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface EarthquakeRepository extends JpaRepository<Earthquake, UUID> {
}
