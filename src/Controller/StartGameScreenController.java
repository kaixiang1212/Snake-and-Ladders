package Controller;

import java.io.IOException;

import View.GameScreen;
import View.PlayerSelectionScreen;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Platform;

// StartScreen Controller communicates between View and Model.

public class StartGameScreenController {
	
	@FXML
	private Button StartGameButton;
	
	@FXML
	private Button ExitGameButton;
	

	
	//private GameScreen game;
	private Stage s;
	
	public StartGameScreenController(Stage s) {
		this.s = s;
	}
	
	@FXML
	public void handleStartGameButton (ActionEvent event) {
		// We always start the game with level 0.
		//Game game = new Game(0, null);
		//LevelIntroScreen introScreen = new LevelIntroScreen(s, game);
		//introScreen.start();
		PlayerSelectionScreen selectionScreen = new PlayerSelectionScreen(s);
		selectionScreen.start();
	}
	
	
	@FXML
	// Exits the Game Application
	public void handleExitGameButton (ActionEvent event) {
		Platform.exit();
	}

}
