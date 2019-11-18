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
    
    public HelpController(){
    	MusicController.initUI();
    }
    
    /**
     * Called when the back button is clicked from the rules screen
     * @throws IOException
     */
    @FXML
    private void backButtonClicked() throws IOException {
    	MusicController.playBack();
    	new StartGameScreen(stage);
        StartGameScreen.start();
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
}