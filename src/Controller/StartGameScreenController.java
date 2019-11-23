package Controller;

import View.BoardSelectionScreen;
import View.HelpGameScreen;
import View.StatsScreen;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.application.Platform;


// StartScreen Controller communicates between View and Model.

public class StartGameScreenController {
	
	@FXML
	private Button StartGameButton;
	@FXML
	private Button statsButton;
	@FXML
	private Button HelpGameButton;
	@FXML
	private Button ExitGameButton;
	
	public StartGameScreenController() {
		MusicController.initUI();
	}

	@FXML
	public void handleStartGameButton (ActionEvent event) {
		MusicController.playNext();
		new BoardSelectionScreen();
		BoardSelectionScreen.start();
	}
	
	@FXML
	// Exits the Game Application
	public void handleExitGameButton (ActionEvent event) {
		MusicController.playBack();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Platform.exit();
	}
	
	@FXML
	public void handleStatsButton (ActionEvent event) {
		MusicController.playNext();
		new StatsScreen();
		StatsScreen.start();	
	}
	
	@FXML
	// goes to rules page
	public void handleHelpButton (ActionEvent event) {
		MusicController.playNext();
		new HelpGameScreen();
		HelpGameScreen.start();	
	}

}
