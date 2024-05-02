package pl.put.poznan.SQC.app.Wizytator;

import pl.put.poznan.SQC.app.KONCIK_STALYCH;
import pl.put.poznan.SQC.app.ScenarioManagement.Scenario;
import pl.put.poznan.SQC.app.ScenarioManagement.Step;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Wizytator sprawdzający poprawność kroku
 */
public class WizytatorSprawdzajacyAktorow implements Wizytator {
    /**
     * Lista kroków niepoprawnych
     */
    ArrayList<String> stepArrayList = new ArrayList<>();
    /**
     * Lista aktorów występujących w scenariuszu
     */
    ArrayList<String> actorsArrayList = new ArrayList<>();

    /**
     * Getter stepArrayList
     * @return lista niepoprawnych kroków
     */
    public ArrayList<String> getStepArrayList() {
        return stepArrayList;
    }

    /**
     * Konstruktor - pobiera aktorów scenariusza
     * @param scenario scenariusz do którego należy Step
     */
    public WizytatorSprawdzajacyAktorow(Scenario scenario) {
        actorsArrayList.addAll(scenario.getActors());
        actorsArrayList.addAll(scenario.getSystemActors());
    }

    /**
     * Metoda odwiedzająca - sprawdza czy krok jest poprawny, jeśli nie to dodaje go do listy kroków niepoprawnych
     * @param step Step do odwiedzenia
     */
    @Override
    public void visit(Step step) {
        // Sprawdza czy pierwsze to aktor, albo jeżeli to słowa warunkowe
        // I nie - nie chciało mi się pisać funkcji, jak się to komuś nie podoba to niech to se pisze se //TODO
        // <------------------------------------------------------------------------------ TAAAAAKAAA LAMBDAAAAAAAAA --------------------------------------------------------------->
        if ((actorsArrayList.stream().noneMatch(s -> step.getStepText().startsWith(s))) && Arrays.stream(KONCIK_STALYCH.keyWords).noneMatch(s -> step.getStepText().startsWith(s))) {
            stepArrayList.add(step.getStepText());
        }
        // Ale szczerze jak wreście znajdę pracę i nie będzie mi sie na tyle nudziło żeby pisać kod o 02 w nocy na projekt do oddania za 3 tygodnie
        // To powinni mi płacić od długości linijki kodu :>>>>> No ale dobra szanujmy się, to w cale nie jest długie
    }
}
