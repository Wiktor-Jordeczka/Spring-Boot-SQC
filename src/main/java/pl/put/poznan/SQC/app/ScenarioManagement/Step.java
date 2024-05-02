package pl.put.poznan.SQC.app.ScenarioManagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.put.poznan.SQC.app.Wizytator.Wizytator;
import pl.put.poznan.SQC.app.Wizytator.WizytatorLiczacyWarunki;

import java.util.ArrayList;

/**
 * Jeden krok w scenariuszu. Obsługuje wizytatorów.
 */
public class Step implements AkceptatoInator{

    /**
     * Numer kroku
     */
    @JsonProperty(index=1)
    private String stepNumber;
    /**
     * Tekst kroku
     */
    @JsonProperty(index=2)
    private String stepText;
    /**
     * Lista podkroków
     */
    private ArrayList<Step> subSteps;

    /**
     * Konstruktor wykorzystywany przy wczytaniu z pliku .json
     * @param stepText tekst kroku
     * @param subSteps liczba podkroków
     */
    @JsonCreator
    public Step(@JsonProperty("step") String stepText,
                @JsonProperty("subSteps") ArrayList<Step> subSteps){
        this.stepText = stepText;
        this.subSteps = subSteps;
    }

    /**
     * Konstruktor kopiujący
     * @param step krok do skopiowania
     */
    public Step(Step step){
        this.stepText = step.stepText;
        this.subSteps = new ArrayList<>();
        if(step.subSteps != null)
            for (Step subStep : step.subSteps)
                this.subSteps.add(new Step(subStep));
        this.stepNumber = step.stepNumber;
    }

    /**
     * Metoda obsługuje wizytatorów z interfejsu Wizytator
     * @param wizytator wizytator do przyjęcia
     * @see Wizytator
     */
    @Override
    public void accept(Wizytator wizytator){
        wizytator.visit(this);
        if(subSteps != null){
            for(Step subStep : subSteps){
                subStep.accept(wizytator);
            }
        }
    }

    /**
     * Oblicza ilość kroków warunkowych (podscenariuszy)
     * @return ilość kroków warunkowych
     */
    public int calculateConditionalSteps(){
        WizytatorLiczacyWarunki wizytatorLiczacyWarunki = new WizytatorLiczacyWarunki();
        this.accept(wizytatorLiczacyWarunki);
        return wizytatorLiczacyWarunki.getLength();
    }

    /**
     * Metoda rekurencyjna usuwa podscenariusze od określonego poziomu
     * @param levels poziom podscenariusza
     */
    public void seekAndDestory(int levels){
        if(levels == 0){
            this.subSteps = null;
        }
        else{
            for(Step subStep : this.subSteps){
                subStep.seekAndDestory(levels - 1);
            }
        }
    }

    /**
     * Setter stepNumber
     * @param stepNumber numer kroku
     */
    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    /**
     * Getter stepNumber
     * @return numer kroku
     */
    public String getStepNumber() {
        return this.stepNumber;
    }

    /**
     * Getter stepText
     * @return tekst kroku
     */
    public String getStepText() {
        return stepText;
    }

    /**
     * Getter subSteps
     * @return lista podkroków
     */
    public ArrayList<Step> getSubSteps() {
        return subSteps;
    }


}
