package app.bootstrap;

import app.entity.Customer;
import app.entity.Dog;
import app.model.DogBreed;
import app.repository.CustomerRepository;
import app.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final DogRepository dogRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadDogData();
        loadCustomerDate();
        System.out.println(dogRepository.count());
    }

    private void loadDogData() {

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

        for (Dog dog : dogRepository.findAll()) {
            System.out.println(dog.getName());
            System.out.println(dog.getId());
            System.out.println(dog.getUpc());
            System.out.println(dog.getVersion());
        }
    }

    private void loadCustomerDate() {

        Customer ai = Customer.builder()
                .id(UUID.randomUUID())
                .name("AI")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer iu = Customer.builder()
                .id(UUID.randomUUID())
                .name("IU")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer celine = Customer.builder()
                .id(UUID.randomUUID())
                .name("Celine")

                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerRepository.saveAll(Arrays.asList(ai, iu, celine));
    }
}