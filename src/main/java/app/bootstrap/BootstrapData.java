package app.bootstrap;

import app.entity.Customer;
import app.entity.Dog;
import app.entity.Volcano;
import app.mapper.VolcanoMapper;
import app.model.DogBreed;
import app.model.VolcanoDTO;
import app.repository.CustomerRepository;
import app.repository.DogRepository;
import app.repository.VolcanoRepository;
import app.service.VolcanoCSV;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final DogRepository dogRepository;
    private final CustomerRepository customerRepository;
    private final VolcanoRepository volcanoRepository;
    private final VolcanoCSV volcanoCSV;
    private final VolcanoMapper volcanoMapper;

    @Override
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
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Dog siberianHusky = Dog.builder()
                .name("Chobo")
                .dogBreed(DogBreed.SIBERIAN_HUSKY)
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Dog alaskanMalamute = Dog.builder()
                .name("Eva")
                .dogBreed(DogBreed.GOLDEN_RETRIEVER)
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        dogRepository.saveAll(Arrays.asList(goldenRetriever, siberianHusky, alaskanMalamute));
    }

    private void loadCustomerDate() {
        customerRepository.deleteAll();
        Customer ai = Customer.builder()
                .id(UUID.randomUUID())
                .name("AI")
                .email("ai@mail.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer iu = Customer.builder()
                .id(UUID.randomUUID())
                .name("IU")
                .email("iu@mail.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer celine = Customer.builder()
                .id(UUID.randomUUID())
                .name("Celine")
                .email("celine@mail.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerRepository.saveAll(Arrays.asList(ai, iu, celine));
    }

    private void loadVolcano() throws FileNotFoundException {
        volcanoRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:csv-data/volcano_db.csv");
        List<VolcanoDTO> volcanoDTOList = volcanoCSV.convertCSV(file);
        for (VolcanoDTO volcanoDTO : volcanoDTOList) {
            Volcano volcano = volcanoMapper.convertVolcanoDTOToVolcano(volcanoDTO);
            volcano.setId(UUID.randomUUID());
            volcanoRepository.save(volcano);
        }

    }
}
