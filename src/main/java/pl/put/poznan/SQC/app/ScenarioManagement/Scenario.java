package pl.put.poznan.SQC.app.ScenarioManagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.put.poznan.SQC.app.Wizytator.WizytatorLiczacy;
import pl.put.poznan.SQC.app.Wizytator.WizytatorLiczacyWarunki;
import pl.put.poznan.SQC.app.Wizytator.WizytatorNazywajacy;
import pl.put.poznan.SQC.app.Wizytator.WizytatorSprawdzajacyAktorow;

import java.util.ArrayList;

/**
 * Klasa przechowuje scenariusz odczytany z pliku jako obiekt.
 * Deleguje wizytatorów z interfejsu Wizytator na obiekty klasy Step.
 */
public class Scenario {

    /**
     * Nazwa scenariusza.
     */
    @JsonProperty(index=1)
    private String scenarioName;
    /**
     * Lista aktorów w scenariuszu.
     */
    @JsonProperty(index=2)
    private ArrayList<String> actors;
    /**
     * Lista aktorów systemowych w scenariuszu.
     */
    @JsonProperty(index=3)
    private ArrayList<String> systemActors;
    /**
     * Lista kroków w scenariuszu.
     */
    private ArrayList<Step> steps;

    /**
     * Konstruktor wykorzystywany przy wczytaniu z pliku .json
     * @param scenarioName nazwa scenariusza
     * @param actors lista aktorów
     * @param systemActors lista aktorów systemowych
     * @param steps lista korków
     */
    @JsonCreator
    public Scenario(@JsonProperty("scenarioName") String scenarioName,
                    @JsonProperty("actors") ArrayList<String> actors,
                    @JsonProperty("systemActors") ArrayList<String> systemActors,
                    @JsonProperty("steps") ArrayList<Step> steps) {
        this.scenarioName = scenarioName;
        this.actors = actors;
        this.systemActors = systemActors;
        this.steps = steps;
        if(steps != null)
            calculateStepIndexes();
    }

    /**
     * Konstruktor kopiujący
     * @param scenario obiekt do skopiowania
     */
    public Scenario(Scenario scenario){
        this(scenario.scenarioName, scenario.actors, scenario.systemActors, null);
        this.steps = new ArrayList<>();
        for(Step step : scenario.steps)
            this.steps.add(new Step(step));
    }

    /**
     * Konwertuje obiekt Scenario na JSON w postaci String-a.
     * @return String zawierający JSON
     * @throws JsonProcessingException problem podczas przetwarzania JSON
     */
    public String scenarioToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter scenarioName
     * @return String scenarioName
     */
    public String getScenarioName(){return this.scenarioName;}

    /**
     * Getter actors
     * @return lista aktorów
     */
    public ArrayList<String> getActors(){
        return this.actors;
    }

    /**
     * Getter systemActors
     * @return lista aktorów systemowych
     */
    public ArrayList<String> getSystemActors(){
        return this.systemActors;
    }

    /**
     * Getter steps
     * @return lista kroków
     */
    public ArrayList<Step> getStepsList(){return this.steps;}

    /**
     * Oblicza ilość kroków w scenariuszu.
     * @return ilość kroków
     */
    public int calculateStepLength(){
        WizytatorLiczacy wizytatorLiczacy = new WizytatorLiczacy();
        for(Step step : steps){
            step.accept(wizytatorLiczacy);
        }
        return wizytatorLiczacy.getLength();
    }

    /**
     * Oblicza ilość poziomów scenariusza.
     * @return ilość poziomów
     */
    public int calculateStepCondition(){
        WizytatorLiczacyWarunki wizytatorLiczacyWarunki = new WizytatorLiczacyWarunki();
        for(Step step : steps){
            step.accept(wizytatorLiczacyWarunki);
        }
        return wizytatorLiczacyWarunki.getLength();
    }

    /**
     * Oblicza kolejne numery kroków scenariusza.
     */
    public void calculateStepIndexes(){
        WizytatorNazywajacy wizytatorNazywajacy = new WizytatorNazywajacy();
        for(Step step : steps){
            step.accept(wizytatorNazywajacy);
        }
    }

    /**
     * Znajduje nieprawidłowe kroki scenariusza
     * @return Lista kroków bez aktora
     */
    public ArrayList<String> calculateMissingActors(){
        WizytatorSprawdzajacyAktorow wizytatorSprawdzajacyAktorow = new WizytatorSprawdzajacyAktorow(this);
        for(Step step : steps){
            step.accept(wizytatorSprawdzajacyAktorow);
        }
        return wizytatorSprawdzajacyAktorow.getStepArrayList();
    }

    /**
     * Ogranicza scenariusz do ustalonego poziomu
     * @param poziom poziom zagłebienia
     * @return Okrojony scenariusz
     * @throws JsonProcessingException problem podczas przetwarzania JSON
     */
    public Scenario calculateLeveledScenario(int poziom) throws JsonProcessingException {
        /// Nad tą linijką poleciały klawiszę, proszę ją uczcić pół minutką ciszy
        Scenario scenario = new Scenario(this);
        // Proszę przemilczmy tę linijkę
        for(Step subStep : scenario.steps){
            int stepLength = subStep.calculateConditionalSteps() ;
            if(stepLength >= poziom){
                subStep.seekAndDestory(poziom - 1);
            }
        }
        return scenario;
    }

}
