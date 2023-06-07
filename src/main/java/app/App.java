package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Set the active profile to "local"
        System.setProperty("spring.profiles.active", "local");
        SpringApplication.run(App.class, args);
    }

}
