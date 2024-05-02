package pl.put.poznan.SQC.app.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.SQC.app.LoggingController;
import pl.put.poznan.SQC.app.ScenarioManagement.Scenario;

import java.util.ArrayList;

/**
 * Klasa obsługująca API
 */
@RestController
@RequestMapping("/scenario")
public class FileController {

    /**
     * Scenariusz pobrany od klienta
     */
    private static Scenario uploadedScenario;

    /**
     * Przechowuje Logger wygenerowany przez LoggerFactory
     */
    private static final Logger logger =  LogManager.getLogger(LoggingController.class);

    /**
     * Odebranie scenariusza
     * @param scenario odebrany scenariusz
     * @return HTTP response
     */
    @PostMapping("/post")
    public ResponseEntity<String> postFile(@RequestPart("json")Scenario scenario){
        String message;
        logger.info("Otrzymałem nowy scenariusz z API\n");
        if(uploadedScenario != null){
            message = "Scenariusz był już przesłany, zmiany zostały nadpisane";
        }
        else message = "Poprawnie przesłano scenariusz";
        logger.info(message);
        uploadedScenario = scenario;
        logger.info("Wysyłam odpowiedź");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * Przesłanie scenariusza
     * @return HTTP response
     * @throws JsonProcessingException problem podczas przetwarzania JSON
     */
    @GetMapping("/get")
    public ResponseEntity<String> getFile() throws JsonProcessingException {
        logger.info("Otrzymałem żądanie przesłania scenariusza");
        if(uploadedScenario == null){
            logger.error("Brak scenariusza\n" +
                    "Wysyłam informację o błędzie");
            return new ResponseEntity<>("Nie przesłano scenariusza", HttpStatus.NOT_FOUND);

        }
        else {
            String val = uploadedScenario.scenarioToJson();
            logger.info("Wysyłam scenariusz w JSON:\n" + val);
            return ResponseEntity.ok(uploadedScenario.scenarioToJson());
        }
    }

    /**
     * Przesłanie ilości kroków w scenariuszu
     * @return HTTP response
     */
    @GetMapping("/getScenarioLength")
    public ResponseEntity<String> getScenarioLength(){
        logger.info("Otrzymałem żądanie przesłania ilości kroków scenariusza");
        if(uploadedScenario == null){
            logger.error("Brak scenariusza\n" +
                    "Wysyłam informację o błędzie");
            return new ResponseEntity<>("Nie przesłano scenariusza", HttpStatus.NOT_FOUND);
        }
        else{
            int val = uploadedScenario.calculateStepLength();
            logger.info("Wysyłam ilość kroków scenariusza: " + val);
            return ResponseEntity.ok(String.valueOf(val));
        }
    }

    /**
     * Przesłanie ilości poziomów scenariusza
     * @return HTTP response
     */
    @GetMapping("/getConditionalLength")
    public ResponseEntity<String> getConditionalLength(){
        logger.info("Otrzymałem żądanie przesłania ilości poziomów scenariusza");
        if(uploadedScenario == null){
            logger.error("Brak scenariusza\n" +
                    "Wysyłam informację o błędzie");
            return new ResponseEntity<>("Nie przesłano scenariusza", HttpStatus.NOT_FOUND);
        }
        else{
            int val = uploadedScenario.calculateStepCondition();
            logger.info("Wysyłam ilość poziomów scenariusza: " + val);
            return ResponseEntity.ok(String.valueOf(val));
        }
    }

    /**
     * Przesłanie listy nieprawidłowych kroków scenariusza
     * @return HTTP response
     */
    @GetMapping("/getMissingActors")
    public ResponseEntity<String> getMissingActors(){
        logger.info("Otrzymałem żądanie przesłania listy nieprawidłowych kroków scenariusza");
        if(uploadedScenario == null){
            logger.error("Brak scenariusza\n" +
                    "Wysyłam informację o błędzie");
            return new ResponseEntity<>("Nie przesłano scenariusza", HttpStatus.NOT_FOUND);
        }
        else{
            ArrayList<String> val = uploadedScenario.calculateMissingActors();
            logger.info("Wysyłam listę nieprawidłowych kroków: \n" + val);
            return ResponseEntity.ok(String.valueOf(val));
        }
    }

    /**
     * Przesłanie scenariusza ograniczonego do ustalonego poziomu
     * @param poziom poziom zagłębienia
     * @return HTTP response
     * @throws JsonProcessingException problem podczas przetwarzania JSON
     */
    @GetMapping("/getLeveledScenario")
    public ResponseEntity<String> getLeveledScenario(@RequestParam(name = "poziom") int poziom) throws JsonProcessingException {
        logger.info("Otrzymałem żądanie przesłania ograniczonego scenariusza");
        if(uploadedScenario == null){
            logger.error("Brak scenariusza\n" +
                    "Wysyłam informację o błędzie");
            return new ResponseEntity<>("Nie przesłano scenariusza", HttpStatus.NOT_FOUND);
        }
        else{
            logger.info("Scenariusz ograniczony do poziomu: " + poziom);
            String val = uploadedScenario.calculateLeveledScenario(poziom).scenarioToJson();
            logger.info("Wysyłam ograniczony scenariusz w JSON:\n " + val);
            return ResponseEntity.ok(String.valueOf(val));
        }
    }
}
