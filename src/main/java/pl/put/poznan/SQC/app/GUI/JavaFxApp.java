package pl.put.poznan.SQC.app.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.put.poznan.SQC.app.KONCIK_STALYCH;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class JavaFxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static TabPane mainTabPanePublic;
    public static Scene scenePublic;

    private void initialize(){
        File path = new File("target", KONCIK_STALYCH.mainFolderName);
        try{
            if(!path.exists()){
                path.mkdirs();
            }
            else{
                //TODO Folder już istnieje
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void attachNewTab(Tab tab){
        // Ustaw plusa na koniec ;; TAK TO JEST GŁUPIE ALE TO NIE MOJA WINA TYLKO JAVY - HA TFU JAVA
        Tab tabPlus = mainTabPanePublic.getSelectionModel().getSelectedItem();
        mainTabPanePublic.getTabs().remove(tabPlus);

        mainTabPanePublic.getTabs().add(tab);
        mainTabPanePublic.getSelectionModel().select(tab);
        mainTabPanePublic.getTabs().add(tabPlus);

       tab.setClosable(true);
    }

    @Override
    public void start(Stage stage) {
        initialize();

        FXMLLoader viewLoader = new FXMLLoader(JavaFxApp.class.getResource("empty-view.fxml"));
        VBox root = null;
        try {
            root = viewLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JavaFxApp.mainTabPanePublic = (TabPane)viewLoader.getNamespace().get("mainTabPane");


        Tab tabPlus = (Tab) viewLoader.getNamespace().get("tabPlus");

        FXMLLoader dropLoader = new FXMLLoader(JavaFxApp.class.getResource("drop-view.fxml"));
        VBox vBox = null;
        try {
            vBox = dropLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tabPlus.setContent(vBox);

        DropViewController dropViewController = new DropViewController();
        dropLoader.setController(dropViewController);

        Scene scene = new Scene(root);
        scenePublic = scene;

        stage.setScene(scene);
        stage.show();
    }
}
