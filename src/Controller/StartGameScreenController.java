package Controller;

import View.PlayerNumSelectionScreen;
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

	private Stage s;

	private MusicController musicController;
	
	public StartGameScreenController(Stage s) {
		this.s = s;
		musicController = new MusicController();
		musicController.initUI();
	}
	
	@FXML
	public void handleStartGameButton (ActionEvent event) {
		musicController.playNext();
		// We always start the game with level 0.
		//Game game = new Game(0, null);
		//LevelIntroScreen introScreen = new LevelIntroScreen(s, game);
		//introScreen.start();
		PlayerNumSelectionScreen selectionScreen = new PlayerNumSelectionScreen(s);
		selectionScreen.start();
//		GameScreen gameScreen = new GameScreen(s);
//		try {
//			gameScreen.start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	@FXML
	// Exits the Game Application
	public void handleExitGameButton (ActionEvent event) {
		musicController.playBack();
		Platform.exit();
	}

}
