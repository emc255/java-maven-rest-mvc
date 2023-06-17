package app.repository;

import app.entity.DogOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DogOrderRepository extends JpaRepository<DogOrder, UUID> {
}
