package pl.put.poznan.SQC.app.ScenarioManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Odczytuje scenariusz z pliku.
 */
public class ScenarioReader {
    /**
     * Wykorzystuje Jackoson ObjectMapper do odczytania pliku .json i konwertuje na obiekt.
     * @param file plik wejściowy
     * @return Scenariusz jako obiekt klasy Scenario.
     * @see Scenario
     * @throws IOException Jeśli nie uda się odczytać pliku.
     */
    public static Scenario readScenarioFromFile(File file) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, Scenario.class);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
