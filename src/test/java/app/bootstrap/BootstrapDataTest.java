package app.bootstrap;

import app.repository.CustomerRepository;
import app.repository.DogRepository;
import app.repository.EarthquakeRepository;
import app.repository.VolcanoRepository;
import app.service.CSVDataToDatabase;
import app.service.CSVDataToDatabaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CSVDataToDatabaseImpl.class)
class BootstrapDataTest {
    @Autowired
    DogRepository dogRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VolcanoRepository volcanoRepository;
    @Autowired
    EarthquakeRepository earthquakeRepository;
    @Autowired
    CSVDataToDatabase CSVDataToDatabase;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(dogRepository,
                customerRepository, volcanoRepository, earthquakeRepository, CSVDataToDatabase);
    }

    @Test
    void testLoadData() throws Exception {
        bootstrapData.run((String) null);
        assertThat(dogRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}