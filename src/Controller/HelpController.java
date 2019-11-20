package Controller;

import java.io.IOException;

import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class HelpController {
    
	@FXML
    private Button backButton;

    public HelpController(){
    	MusicController.initUI();
    }
    
    /**
     * Called when the back button is clicked from the rules screen
     * @throws IOException
     */
    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
    	MusicController.playBack();
        StartGameScreen.start();
    }
    
}