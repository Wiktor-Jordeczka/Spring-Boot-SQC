package pl.put.poznan.SQC.app.API;

import org.junit.jupiter.api.*;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.hierarchical.Node;
import pl.put.poznan.SQC.app.ScenarioManagement.Scenario;
import pl.put.poznan.SQC.app.ScenarioManagement.ScenarioReader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class FileControllerTest {
    private final ArrayList<Scenario> testScenarios = new ArrayList<>();

    public Scenario getItemByName(String name){
        for(Scenario scenario : testScenarios){
            if(name.equals(scenario.getScenarioName()))
                return scenario;
        }
        return null;
    }

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        URI dir = new URI(String.valueOf(getClass().getResource("/")));
        File[] files = (new File(dir).listFiles((dir1, name) -> name.toLowerCase().endsWith(".json")));
        assert files != null;
        for(File filePath : files){
            testScenarios.add(ScenarioReader.readScenarioFromFile(filePath));
        }
    }


    private DynamicTest lengthTest(String name, int expectedLength){
        return DynamicTest.dynamicTest(name + " length", () ->
                assertEquals(expectedLength, getItemByName(name).calculateStepLength()));
    }

    @TestFactory
    Collection<DynamicTest> getScenarioLength(){
        return List.of(
                lengthTest("Bibliotekarz", 12),
                lengthTest("BozeJava", 20),
                lengthTest("Przepis", 17),
                lengthTest("Wypozyczalnia", 22)
        );
    }


    private DynamicTest conditionalLengthTest(String name, int expectedLength){
        return DynamicTest.dynamicTest(name + " conditional length", () ->
                assertEquals(expectedLength, getItemByName(name).calculateStepCondition()));
    }

    @TestFactory
    Collection<DynamicTest> getConditionalLength() {
        return List.of(
                conditionalLengthTest("Bibliotekarz", 2),
                conditionalLengthTest("BozeJava", 3),
                conditionalLengthTest("Przepis", 4),
                conditionalLengthTest("Wypozyczalnia", 6)
        );
    }


    private DynamicTest getMissingActorsTest(String name, ArrayList<String> expectedReturn){
        return DynamicTest.dynamicTest(name + " conditional length", () ->
                assertEquals(expectedReturn, getItemByName(name).calculateMissingActors()));
    }
    @TestFactory
    Collection<DynamicTest> getMissingActors() {
        return List.of(
                getMissingActorsTest("Bibliotekarz", new ArrayList<>(List.of("Wyświetla się formularz.")))
                // BOŻE NIE CHCE MI SIĘ
                //getMissingActorsTest("BozeJava", new ArrayList<>(List.of("Podczas kompilacji, IDE zgłasza błędy.",""))),
                //getMissingActorsTest("Przepis", new ArrayList<>(List.of("Wyświetla się formularz."))),
                //getMissingActorsTest("Wypozyczalnia", new ArrayList<>(List.of("Wyświetla się formularz.")))

        );
    }
}
