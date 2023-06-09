package app.bootstrap;

import app.entity.Customer;
import app.entity.Dog;
import app.entity.Volcano;
import app.model.DogBreed;
import app.model.VolcanoDTO;
import app.repository.CustomerRepository;
import app.repository.DogRepository;
import app.repository.VolcanoRepository;
import app.service.VolcanoCSV;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final DogRepository dogRepository;
    private final CustomerRepository customerRepository;
    private final VolcanoRepository volcanoRepository;
    private final VolcanoCSV volcanoCSV;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadDogData();
        loadCustomerDate();
        loadVolcano();
    }

    private void loadDogData() {
        dogRepository.deleteAll();
        Dog goldenRetriever = Dog.builder()
                .name("Thor")
                .dogBreed(DogBreed.ALASKAN_MALAMUTE)
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .build();

        Dog siberianHusky = Dog.builder()
                .name("Chobo")
                .dogBreed(DogBreed.SIBERIAN_HUSKY)
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .build();

        Dog alaskanMalamute = Dog.builder()
                .name("Eva")
                .dogBreed(DogBreed.GOLDEN_RETRIEVER)
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .build();

        dogRepository.saveAll(Arrays.asList(goldenRetriever, siberianHusky, alaskanMalamute));
    }

    private void loadCustomerDate() {
        customerRepository.deleteAll();
        Customer ai = Customer.builder()
                .name("AI")
                .email("ai@mail.com")
                .version(1)
                .build();

        Customer iu = Customer.builder()
                .name("IU")
                .email("iu@mail.com")
                .version(1)
                .build();

        Customer celine = Customer.builder()
                .name("Celine")
                .email("celine@mail.com")
                .version(1)
                .build();

        customerRepository.saveAll(Arrays.asList(ai, iu, celine));
    }

    private void loadVolcano() throws FileNotFoundException {
        volcanoRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:csv-data/volcano_db.csv");
        List<VolcanoDTO> volcanoDTOList = volcanoCSV.convertCSV(file);
        for (VolcanoDTO volcanoDTO : volcanoDTOList) {
            Volcano volcano = Volcano.builder()
                    .name(volcanoDTO.getName())
                    .country(volcanoDTO.getCountry())
                    .region(volcanoDTO.getRegion())
                    .latitude(volcanoDTO.getLatitude())
                    .longitude(volcanoDTO.getLongitude())
                    .elevation(volcanoDTO.getElevation())
                    .type(volcanoDTO.getType())
                    .status(volcanoDTO.getStatus())
                    .build();
            volcanoRepository.save(volcano);
        }

    }
}
