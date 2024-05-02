package pl.put.poznan.SQC.app.Wizytator;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import pl.put.poznan.SQC.app.KONCIK_STALYCH;
import pl.put.poznan.SQC.app.ScenarioManagement.Scenario;
import pl.put.poznan.SQC.app.ScenarioManagement.Step;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WizytatorTest {
    private DynamicTest wizytatorLiczacyTest(int expectedLength){
        return DynamicTest.dynamicTest("Test " + expectedLength + " kroków", () -> {
            Step step=mock(Step.class);
            when(step.getStepText()).thenReturn("Weź idź");
            WizytatorLiczacy wizytator=new WizytatorLiczacy();
            for (int i=0;i<expectedLength;i++)
                wizytator.visit(step);
            assertEquals(expectedLength,wizytator.getLength());
        });

    }

    @TestFactory
    Collection<DynamicTest> getScenarioLengthVisitator(){
        return List.of(
                wizytatorLiczacyTest(10),
                wizytatorLiczacyTest(15),
                wizytatorLiczacyTest(20),
                wizytatorLiczacyTest(25)
        );
    }

    private DynamicTest wizytatorLiczacyWarunkiTest(int expectedLength){
        return DynamicTest.dynamicTest("Test " + expectedLength + " kroków warunkowych", () -> {
            Step step=mock(Step.class);
            when(step.getStepText()).thenReturn(KONCIK_STALYCH.keyWords[0] +"dotknąć trawy");
            WizytatorLiczacyWarunki wizytator=new WizytatorLiczacyWarunki();
            for (int i=0;i<expectedLength;i++)
                wizytator.visit(step);
            assertEquals(expectedLength,wizytator.getLength());
        });
    }


    @TestFactory
    Collection<DynamicTest> getConditionalLengthVisitatorTest(){
        return List.of(
                wizytatorLiczacyWarunkiTest(10),
                wizytatorLiczacyWarunkiTest(15),
                wizytatorLiczacyWarunkiTest(20),
                wizytatorLiczacyWarunkiTest(25),
                wizytatorLiczacyWarunkiTest(30)
        );
    }
// Tak, chciałem to zrobić lepiej ale nie mam na to czasu - robiłem 15 minut z zegarkiem - nie wyszło i rzuciłem
    // I widzisz, umiem być konsekwentny

    private DynamicTest wizytatorSprawdzajacyAktorow(
            ArrayList<String> aktorzy, ArrayList<String> system, String text, ArrayList<String> returnText) {
        return DynamicTest.dynamicTest("Test " + aktorzy + " aktorów", () -> {
                Step step = mock(Step.class);
            Scenario scenario = mock(Scenario.class);
            when(scenario.getActors()).thenReturn(aktorzy);
            when(scenario.getSystemActors()).thenReturn(system);
            WizytatorSprawdzajacyAktorow wizytator = new WizytatorSprawdzajacyAktorow(scenario);
            Arrays.stream(text.split("[,|.|?|!]")).forEach(part -> {
                part = part.strip();
                when(step.getStepText()).thenReturn(part);
                wizytator.visit(step);
            });
            assertEquals(returnText,wizytator.getStepArrayList());
        });
    }

    @TestFactory
    Collection<DynamicTest> getActorsReturnListVisitatorTest(){
        return List.of(
                wizytatorSprawdzajacyAktorow(
                        new ArrayList<>(Arrays.asList("Ja","sułtan","syn Mehmeda", "brat Słońca i Księżyca", "wnuk i namiestnik Boga")),
                        new ArrayList<>(Arrays.asList("Pan królestw Macedonii")),
                        "Ja, sułtan, syn Mehmeda, brat Słońca i Księżyca, wnuk i namiestnik Boga, Pan królestw Macedonii, Babilonu, Jerozolimy, Wielkiego i Małego Egiptu, Król nad Królami, Pan nad Panami",
                        new ArrayList<>(Arrays.asList("Babilonu", "Jerozolimy", "Wielkiego i Małego Egiptu", "Król nad Królami",
                                "Pan nad Panami"))),
                wizytatorSprawdzajacyAktorow(
                        new ArrayList<>(Arrays.asList("znamienity rycerz","niezwyciężony dowódca","niepokonany", "wypełniający ")),
                        new ArrayList<>(Arrays.asList("nadzieja","Kozakom")),
                        "znamienity rycerz, niezwyciężony dowódca, niepokonany obrońca miasta Pańskiego, wypełniający wolę samego Boga, nadzieja i uspokojenie dla muzułmanów, budzący przestrach, ale i wielki obrońca chrześcijan — nakazuję wam, Kozakom zaporoskim, poddać się mi dobrowolnie bez żadnego oporu i nie kazać mi się więcej waszymi napaściami przejmować",
                        new ArrayList<>(Arrays.asList("budzący przestrach","ale i wielki obrońca chrześcijan — nakazuję wam","poddać się mi dobrowolnie bez żadnego oporu i nie kazać mi się więcej waszymi napaściami przejmować"))

                ));


    }

    @Test
    void testSprawdzajacyAktorow() {
        Step step=mock(Step.class);
        Scenario scenario=mock(Scenario.class);
        when(scenario.getActors()).thenReturn(new ArrayList<String>() {
            {
                add("Ty");
                add("sułtanie");
            }
        });
        when(scenario.getSystemActors()).thenReturn(new ArrayList<String>() {
            {
                add("diable turecki,");
            }
        });
        WizytatorSprawdzajacyAktorow wizytator=new WizytatorSprawdzajacyAktorow(scenario);
        when(step.getStepText()).thenReturn("Ty,");
        wizytator.visit(step);
        when(step.getStepText()).thenReturn("sułtanie,");
        wizytator.visit(step);
        when(step.getStepText()).thenReturn("diable turecki,");
        wizytator.visit(step);
        assertEquals(wizytator.getStepArrayList().isEmpty(),true);
        when(step.getStepText()).thenReturn("IF: przeklętego diabła bracie i towarzyszu,");
        wizytator.visit(step);
        assertEquals(wizytator.getStepArrayList().isEmpty(),true);
        when(step.getStepText()).thenReturn("IF: samego Lucyfera sekretarzu");
        wizytator.visit(step);
        assertEquals(wizytator.getStepArrayList().isEmpty(),true);
        when(step.getStepText()).thenReturn("Jaki z ciebie do diabła rycerz,");
        wizytator.visit(step);
        assertEquals(wizytator.getStepArrayList().isEmpty(),false);
        when(step.getStepText()).thenReturn("jeśli nie umiesz gołą **** jeża zabić.");
        wizytator.visit(step);
        assertEquals(2,wizytator.getStepArrayList().size());
    }

    @Test
    void testNazywajacy() {
        Step step1=mock(Step.class);
        Step step11=mock(Step.class);
        Step step12=mock(Step.class);
        Step step111=mock(Step.class);
        Step step112=mock(Step.class);
        when(step112.getSubSteps()).thenReturn(null);
        when(step111.getSubSteps()).thenReturn(null);
        when(step12.getSubSteps()).thenReturn(null);
        when(step11.getSubSteps()).thenReturn(new ArrayList<Step>() {
            {
                add(step111);
                add(step112);
            }
        });
        when(step1.getSubSteps()).thenReturn(new ArrayList<Step>() {
            {
                add(step11);
                add(step12);
            }
        });
        WizytatorNazywajacy wizytator=new WizytatorNazywajacy();
        //Jak to niby dziala
        wizytator.visit(step1);

    }
}
