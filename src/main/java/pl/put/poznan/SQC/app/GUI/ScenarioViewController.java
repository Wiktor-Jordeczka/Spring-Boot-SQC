package pl.put.poznan.SQC.app.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class ScenarioViewController {

    @FXML
    private TitledPane actorTilePaneId;

    @FXML
    private TitledPane actorSystemListId;

    @FXML
    private TitledPane scenarioPane;



    @FXML
    void initialize() {

        //scenarioPane.minHeightProperty().bind(SQCApplication.scenePublic.heightProperty());
    }
}
