package pl.put.poznan.SQC.app.Wizytator;

import pl.put.poznan.SQC.app.ScenarioManagement.Step;

/**
 * Wizytator zliczający liczbę kroków
 */
public class WizytatorLiczacy implements Wizytator{
    /**
     * Ilość odwiedzonych kroków
     */
    private int length = 0;

    /**
     * Getter length
     * @return ilość odwiedzonych kroków
     */
    public int getLength(){
        return length;
    }

    /**
     * Metoda odwiedzająca - zwiększa ilość odwiedzonych o 1
     * @param step obiekt Step do odwiedzenia
     */
    @Override
    public void visit(Step step){
        length++;
    }
}
