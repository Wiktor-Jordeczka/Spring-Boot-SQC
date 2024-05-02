package pl.put.poznan.SQC.app.Wizytator;

import pl.put.poznan.SQC.app.ScenarioManagement.Step;

/**
 * Interfejs Wizytatora obsługiwany przez Klasę Step.
 * Obiekty z tego interfejsu analizują scenariusze i podscenariusze.
 * @see Step
 */
public interface Wizytator {
    /**
     * Metoda odwiedzająca
     * @param step Step do odwiedzenia
     */
    void visit(Step step);
}
