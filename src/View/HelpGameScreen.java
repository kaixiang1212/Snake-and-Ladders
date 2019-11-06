package View;

import java.io.IOException;

import Controller.HelpController;
import Controller.PlayerCustomizationController;
import Controller.PlayerNumSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpGameScreen {
    private Stage stage;
    private String screenTitle;
    private FXMLLoader fxmlLoader;

    public HelpGameScreen(Stage stage) {
        this.stage = stage;
        this.screenTitle = "help screen";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("fxml/HelpScreen.fxml"));
    }

    public void start() {
        stage.setTitle(screenTitle);
        try {
            Parent root = fxmlLoader.load();
            ((HelpController )fxmlLoader.getController()).setStage(stage);

            
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
