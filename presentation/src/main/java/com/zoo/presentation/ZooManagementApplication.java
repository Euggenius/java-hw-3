package com.zoo.presentation;

import com.zoo.application.dto.CreateAnimalRequest;
import com.zoo.application.dto.CreateEnclosureRequest;
import com.zoo.application.service.AnimalService;
import com.zoo.application.service.EnclosureService;
import com.zoo.domain.animal.model.Gender;
import com.zoo.domain.enclosure.model.EnclosureId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;


@SpringBootApplication
@ComponentScan(basePackages = {"com.zoo.domain", "com.zoo.application", "com.zoo.infrastructure", "com.zoo.presentation"})
public class ZooManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZooManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(AnimalService animalService, EnclosureService enclosureService) {
        return args -> {
            System.out.println("Initializing database with sample data...");
            try {
                EnclosureId predatorEnclosureId = EnclosureId.fromString(enclosureService.addEnclosure(new CreateEnclosureRequest("PREDATOR_LARGE", 100.0, 5)).id().toString());
                EnclosureId herbivoreEnclosureId = EnclosureId.fromString(enclosureService.addEnclosure(new CreateEnclosureRequest("HERBIVORE_SMALL", 50.0, 10)).id().toString());
                 System.out.println("Created enclosures: " + predatorEnclosureId + ", " + herbivoreEnclosureId);

                animalService.addAnimal(new CreateAnimalRequest("Lion", true, "Simba", LocalDate.of(2020, 1, 15), Gender.MALE, "Meat"));
                animalService.addAnimal(new CreateAnimalRequest("Zebra", false, "Marty", LocalDate.of(2021, 5, 10), Gender.MALE, "Grass"));
                animalService.addAnimal(new CreateAnimalRequest("Meerkat", false, "Timon", LocalDate.of(2022, 8, 1), Gender.MALE, "Insects"));
                 System.out.println("Added sample animals.");

            } catch (Exception e) {
                 System.err.println("Error initializing data: " + e.getMessage());
                 e.printStackTrace();
            }
            System.out.println("Database initialization complete.");
        };
    }

}
