package pl.put.poznan.SQC.app.Wizytator;

import pl.put.poznan.SQC.app.KONCIK_STALYCH;
import pl.put.poznan.SQC.app.ScenarioManagement.Step;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Wizytator ustawiający numer kroku dla każdego kroku
 */
public class WizytatorNazywajacy implements Wizytator{
    /**
     * Lista indeksów kroków odwiedzonych. Domyślnie {0}
     */
    private ArrayList<Integer> visitedIndex = new ArrayList<>(Collections.nCopies(1,0));
    /**
     * Lista indeksów kroków do odwiedzenia
     */
    private ArrayList<Integer> leftToVisit = new ArrayList<>();
    /**
     * Poziom zagłębienia kroku (poziom podscenariusza) - domyślnie 0
     */
    private int deepLevel = 0;

    /**
     * Metoda odwiedzająca - Ustawia numer kroku, jeżeli istnieją to ustawia numery podkroków.
     * @param step Step do odwiedzenia
     */
    @Override
    public void visit(Step step) {
        visitedIndex.set(deepLevel, visitedIndex.get(deepLevel) + 1);
        step.setStepNumber(KONCIK_STALYCH.tablicoNaStringoInator(visitedIndex));
        if (step.getSubSteps() != null) {
            leftToVisit.add(step.getSubSteps().size());
            deepLevel++;
            visitedIndex.add(0);
        } else if (deepLevel != 0) {
            leftToVisit.set(deepLevel - 1, leftToVisit.get(deepLevel -1) - 1);
            while (leftToVisit.get(deepLevel -1) <= 0) {
                leftToVisit.remove(deepLevel - 1);
                visitedIndex.remove(deepLevel);
                deepLevel--;

                // Drzewo też ma w sobie podDrzewo w indeksie... ale pewnie trzeba to będzie sprawdzić w jakimś teście
                if(deepLevel==0) break;
                leftToVisit.set(deepLevel - 1, leftToVisit.get(deepLevel -1) - 1);
            }
        }
    }
}
