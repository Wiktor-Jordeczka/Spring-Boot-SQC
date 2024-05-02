package pl.put.poznan.SQC.app.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Klasa aplikacji SpringBoot - konfigurowana automatycznie
 */
@SpringBootApplication
public class SpringBootApp {

    /**
     * Uruchamia serwer do API za pomocą SpringBoot
     * @param args parametry z linii poleceń
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
