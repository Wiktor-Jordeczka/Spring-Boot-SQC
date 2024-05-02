package pl.put.poznan.SQC.app.ScenarioManagement;

import pl.put.poznan.SQC.app.Wizytator.Wizytator;

/**
 * Interfejs obiektu odwiedzanego dla Wizytatora
 */
public interface AkceptatoInator {
    /**
     * Metoda obsługuje wizytatorów z interfejsu Wizytator
     * @param wizytator wizytator do przyjęcia
     * @see Wizytator
     */
    void accept(Wizytator wizytator);
}
