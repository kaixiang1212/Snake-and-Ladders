package Controller;

import java.io.IOException;

import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
    
	@FXML
    private Button backButton;
	
	private Stage stage;
    private MusicController musicController;
    
    public HelpController(){
    	musicController = new MusicController();
    	musicController.initUI();
    }
    
    /**
     * Called when the back button is clicked from the rules screen
     * @throws IOException
     */
    @FXML
    private void backButtonClicked() throws IOException {
    	musicController.playBack();
    	StartGameScreen startGameScreen = new StartGameScreen(stage);
        startGameScreen.start();
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
}