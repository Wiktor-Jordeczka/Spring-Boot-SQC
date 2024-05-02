package pl.put.poznan.SQC.app.Wizytator;

import pl.put.poznan.SQC.app.KONCIK_STALYCH;
import pl.put.poznan.SQC.app.ScenarioManagement.Step;

import java.util.Arrays;

/**
 * Wizytator zliczający ilość słów warunkowych
 */
public class WizytatorLiczacyWarunki implements Wizytator{
    /**
     * Ilość słów warunkowych
     */
    private int length = 0;

    /**
     * Getter length
     * @return ilość słów warunkowych
     */
    public int getLength() {
        return length;
    }

    /**
     * Metoda odwiedzająca - zwiększa length o 1 jeśli krok zawiera keyword
     * @param step Step do odwiedzenia
     */
    @Override
    public void visit(Step step) {
        if(Arrays.stream(KONCIK_STALYCH.keyWords).anyMatch(s -> step.getStepText().startsWith(s))){
            length++;
        }
    }
}
