package app.repository;

import app.entity.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DogRepositoryTest {
    @Autowired
    DogRepository dogRepository;

    @Test
    void testSaveDog() {
        Dog saveDog = dogRepository.save(Dog.builder()
                .name("Brute")
                .build());
        assertThat(saveDog).isNotNull();
        assertThat(saveDog.getId()).isNotNull();

    }
}