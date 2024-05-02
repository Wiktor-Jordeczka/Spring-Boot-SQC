package pl.put.poznan.SQC.app.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import pl.put.poznan.SQC.app.KONCIK_STALYCH;
import pl.put.poznan.SQC.app.ScenarioManagement.Scenario;
import pl.put.poznan.SQC.app.ScenarioManagement.ScenarioReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;


public class DropViewController {

    @FXML
    private VBox vBox;

    @FXML
    private Label label;

    @FXML
    void initialize() {
        vBox.setOnDragOver(dragEvent -> {
            if (dragEvent.getDragboard().hasFiles()) {
                // Tak to spagetti bolono, sprawdź czy każdy z plików jest jsonem
                boolean allValid = dragEvent.getDragboard().getFiles().stream().allMatch(file ->
                    Arrays.asList(KONCIK_STALYCH.supportedExtensions).
                            contains(FilenameUtils.getExtension(String.valueOf(file))));

                if(allValid){
                    label.setText("Czy chcesz przesłać " + dragEvent.getDragboard().getFiles().size() + " plików?");
                    dragEvent.acceptTransferModes(TransferMode.COPY);
                }
                else{
                    label.setText("Błędny format pliku");
                }
            }
            dragEvent.consume();
        });

        vBox.setOnDragDropped(droppedEvent -> {
            Dragboard db = droppedEvent.getDragboard();
            if(db.hasFiles()){
                for(File file : db.getFiles()){
                    try {
                        File path = new File("target", KONCIK_STALYCH.mainFolderName + "/" + file.getName());
                        Files.copy(file.getAbsoluteFile().toPath(), path.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        Scenario scenario =  ScenarioReader.readScenarioFromFile(path);
                        ScenarioTranformator transformator = new ScenarioTranformator(scenario);
                        JavaFxApp.attachNewTab( transformator.scenarioTabGenerator());

                    }   catch (FileAlreadyExistsException e) {
                       //TODO
                    } catch (IOException e) {
                       //TODO
                    }
                }
            }
        label.setText("Upuść plik aby go otworzyć");
        droppedEvent.setDropCompleted(true);
        droppedEvent.consume();
    });
    }

}
