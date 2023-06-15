package app.bootstrap;

import app.entity.Customer;
import app.entity.Dog;
import app.entity.Earthquake;
import app.entity.Volcano;
import app.model.VolcanoDTO;
import app.model_csv.EarthquakeCSV;
import app.repository.CustomerRepository;
import app.repository.DogRepository;
import app.repository.EarthquakeRepository;
import app.repository.VolcanoRepository;
import app.service.CSVDataToDatabase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final DogRepository dogRepository;
    private final CustomerRepository customerRepository;
    private final VolcanoRepository volcanoRepository;
    private final EarthquakeRepository earthquakeRepository;
    private final CSVDataToDatabase CSVDataToDatabase;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadDogData();
        loadCustomerDate();
        loadVolcano();
        loadEarthquake();
    }

    private void loadDogData() {
        dogRepository.deleteAll();
        Dog goldenRetriever = Dog.builder()
                .name("Thor")
                .breed("Alaskan Malamute")
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .build();

        Dog siberianHusky = Dog.builder()
                .name("Chobo")
                .breed("SIBERIAN_HUSKY")
                .upc(String.valueOf(new Random().nextInt(900000) + 100000))
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .build();

        Dog alaskanMalamute = Dog.builder()
                .name("Eva")
                .breed("Golden Retriever")
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
        List<VolcanoDTO> volcanoDTOList = CSVDataToDatabase.volcanoCSV(file);
        List<Volcano> volcanoList = new ArrayList<>();
        for (VolcanoDTO volcanoDTO : volcanoDTOList) {
            Volcano volcano = Volcano.builder()
                    .name(volcanoDTO.getName())
                    .country(volcanoDTO.getCountry())
                    .region(volcanoDTO.getRegion())
                    .latitude(Math.round(volcanoDTO.getLatitude() * 100.0) / 100.0)
                    .longitude(Math.round(volcanoDTO.getLongitude() * 100.0) / 100.0)
                    .elevation(volcanoDTO.getElevation())
                    .type(volcanoDTO.getType())
                    .status(volcanoDTO.getStatus())
                    .build();
            volcanoList.add(volcano);

        }
        volcanoRepository.saveAll(volcanoList);
    }

    private void loadEarthquake() throws FileNotFoundException {
        earthquakeRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:csv-data/earthquakes.csv");
        List<EarthquakeCSV> earthquakeCSVList = CSVDataToDatabase.earthquakeCSV(file);
        List<Earthquake> earthquakeList = new ArrayList<>();
        for (EarthquakeCSV earthquakeCSV : earthquakeCSVList) {
            Earthquake earthquake = Earthquake.builder()
                    .eruptionDate(createLocalDateTime(earthquakeCSV.getEruptionDate()))
                    .latitude(Math.round(earthquakeCSV.getLatitude() * 100.0) / 100.0)
                    .longitude(Math.round(earthquakeCSV.getLongitude() * 100.0) / 100.0)
                    .magnitude(earthquakeCSV.getMagnitude())
                    .build();
            earthquakeList.add(earthquake);
        }
        earthquakeRepository.saveAll(earthquakeList);
    }

    private LocalDateTime createLocalDateTime(String date) {
        Random random = new Random();

        int hr = random.nextInt(24);
        String hrString = hr >= 10 ? String.valueOf(hr) : "0" + hr;
        random = new Random();
        int minute = random.nextInt(60);
        String minuteString = minute >= 10 ? String.valueOf(minute) : "0" + minute;
        random = new Random();
        int sec = random.nextInt(60);
        String secString = sec >= 10 ? String.valueOf(sec) : "0" + sec;

        date = date + " " + hrString + ":" + minuteString + ":" + secString;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        return LocalDateTime.parse(date, formatter);
    }
}
