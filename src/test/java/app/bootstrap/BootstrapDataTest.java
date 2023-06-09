package app.bootstrap;

import app.repository.CustomerRepository;
import app.repository.DogRepository;
import app.repository.VolcanoRepository;
import app.service.VolcanoCSV;
import app.service.VolcanoCSVImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(VolcanoCSVImpl.class)
class BootstrapDataTest {
    @Autowired
    DogRepository dogRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VolcanoRepository volcanoRepository;
    @Autowired
    VolcanoCSV volcanoCSV;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(dogRepository,
                customerRepository, volcanoRepository, volcanoCSV);
    }

    @Test
    void testLoadData() throws Exception {
        bootstrapData.run(null);
        assertThat(dogRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}