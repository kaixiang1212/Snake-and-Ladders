package Controller;

import java.io.IOException;

import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
    private Stage stage;
    public HelpController(){}

    @FXML
    private Button backButton;
    @FXML
    private void backButtonClicked() throws IOException {
        StartGameScreen startGameScreen = new StartGameScreen(stage);
        startGameScreen.start();
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
}