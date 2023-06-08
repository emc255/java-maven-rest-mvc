package app.repository;

import app.entity.Volcano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VolcanoRepository extends JpaRepository<Volcano, UUID> {
}
