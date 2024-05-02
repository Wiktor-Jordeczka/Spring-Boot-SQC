package pl.put.poznan.SQC.app.GUI;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import pl.put.poznan.SQC.app.KONCIK_STALYCH;
import pl.put.poznan.SQC.app.ScenarioManagement.Scenario;
import pl.put.poznan.SQC.app.ScenarioManagement.Step;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class ScenarioTranformator {

   //TODO  TitledPane scenariuszPane;

    private Scenario scenario;
    private FXMLLoader scenarioViewLoader;
    private ScrollPane root;

    ScenarioTranformator(Scenario scenario){
        try {
            this.scenarioViewLoader = new FXMLLoader(ScenarioTranformator.class.getResource("scenario-view.fxml"));
            this.scenario = scenario;
            this.root = scenarioViewLoader.load();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    void generateScenariuszHBox(){
        TextField scenarioName = (TextField) scenarioViewLoader.getNamespace().get("scenarioNameId");
        scenarioName.setText(scenario.getScenarioName());

        Label scenarioLength = (Label)scenarioViewLoader.getNamespace().get("scenarioLength");
        scenarioLength.setText(Integer.toString(scenario.calculateStepLength()));
        Label conditionalLength = (Label)scenarioViewLoader.getNamespace().get("conditionalLength");
        conditionalLength.setText(Integer.toString(scenario.calculateStepCondition()));
    }

    /**
     * Aktorzy zwykil
     */
    void generateAktorzyPane(){
        ListView<String> actorsListId = (ListView<String>)scenarioViewLoader.getNamespace().get("actorsListId");
        actorsListId.prefHeightProperty().bind(Bindings.size(actorsListId.getItems()).multiply(KONCIK_STALYCH.dlugoscElementyWLiscie));
        for (String actor : scenario.getActors()) {
            actorsListId.getItems().add(actor);
        }
        actorsListId.minWidthProperty().bind(JavaFxApp.scenePublic.widthProperty().divide(2));
        actorsListId.maxWidthProperty().bind(JavaFxApp.scenePublic.widthProperty().divide(2));
    }

    /**
     *  Aktorzy systemowi
     */
    void generateAktorzySystemowiPane(){
        ListView<String> actorsSystemListId = (ListView<String>) scenarioViewLoader.getNamespace().get("actorsSystemListId");
        actorsSystemListId.prefHeightProperty().bind(Bindings.size(actorsSystemListId.getItems()).multiply(KONCIK_STALYCH.dlugoscElementyWLiscie));
        for (String actor : scenario.getSystemActors()) {
            actorsSystemListId.getItems().add(actor);
        }
        actorsSystemListId.minWidthProperty().bind(JavaFxApp.scenePublic.widthProperty().divide(2));
        actorsSystemListId.maxWidthProperty().bind(JavaFxApp.scenePublic.widthProperty().divide(2));
    }

    /**
     * Generator gałęzi dla scenariusza ||| Broke AF
     * @param step
     * @param root
     * @return
     */
    TreeItem<String> stepGenrator(Step step, TreeItem<String> root){
        for (Step subStep : step.getSubSteps()) {
            if(subStep.getSubSteps() == null){
                root.getChildren().add(new TreeItem<>(subStep.getStepNumber() + " " + subStep.getStepText()));
            }
            else {
                TreeItem<String> treeItem = new TreeItem<>(subStep.getStepNumber() + " " + subStep.getStepText());
                root.getChildren().add(stepGenrator(subStep,treeItem));
            }
        }
        return root;
    }

    /**
     * Scenariusz
     */
    void generateScenariuszTree(){
        VBox treeContainerBox = (VBox) scenarioViewLoader.getNamespace().get("treeContainerBox");
        for(Step step : scenario.getStepsList()){
            TreeItem<String> treeRoot = new TreeItem<>(step.getStepNumber() + " " + step.getStepText());
            TreeView<String> treeView = new TreeView<>(treeRoot);
            if(step.getSubSteps() != null) {
                stepGenrator(step,treeRoot);
            }
            treeContainerBox.getChildren().add(treeView);
            if(treeRoot.getChildren().isEmpty()){
                treeView.minHeightProperty().set(KONCIK_STALYCH.dlugoscElementyWLiscie );
                treeView.maxHeightProperty().set(KONCIK_STALYCH.dlugoscElementyWLiscie );
            }
            else{
                IntegerProperty expandedCount = new SimpleIntegerProperty(treeView.getExpandedItemCount());
/*                try {
                    treeView.getScene().getRoot().addEventHandler(TreeItem.expandedItemCountChangeEvent(),
                            evt -> treeView.getScene().getRoot().requestLayout());
                }
                catch (Exception e){
                    System.out.println(e);
                }*/

                expandedCount.bind(treeView.expandedItemCountProperty());

                treeView.minHeightProperty().bind(expandedCount.multiply(KONCIK_STALYCH.dlugoscElementyWLiscie));
                treeView.maxHeightProperty().bind(expandedCount.multiply(KONCIK_STALYCH.dlugoscElementyWLiscie));



            }
        }
    }

    Tab scenarioTabGenerator() {
        // Factory - dependencies -
        generateScenariuszHBox();
        generateAktorzyPane();
        generateAktorzySystemowiPane();
        generateScenariuszTree();

        Tab tab = new Tab(scenario.getScenarioName());
        tab.setContent(root);
        root.minHeightProperty().bind(JavaFxApp.scenePublic.heightProperty());

        return tab;

    }

}
