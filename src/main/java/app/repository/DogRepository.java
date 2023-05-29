package app.repository;

import app.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DogRepository extends JpaRepository<Dog, UUID> {

}
