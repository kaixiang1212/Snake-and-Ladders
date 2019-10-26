package Sneks_and_Ladders;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class GameScreen implements EventHandler<KeyEvent> {
	
	private Stage stage;
	private String title;
	private Scene scene;
	private BoardController boardcontroller;
	private Game game;

	public GameScreen(Stage s) {
		this.stage = stage;
		this.title = "Sneks & Ladders";
		this.game = game;
	}

	public void start() throws IOException {
		loadGameScreen(this.stage, this.game);
	}
	
	@Override
	public void handle(KeyEvent event) {
		boardcontroller.handleKeyPress(event);
	}
	
	public Game getGame() {
		return game;
	}
	
	

	// NOTE--> Probably the part where you choose the Board
	public BoardEntityLoader loadJsonBoard(Stage stage, Game game) throws IOException {
		
		//String filename;
		//int currentBoard = game.getcurrentBoard();
		
		/**
		switch (currentBoard) {
		case 0:
			filename = "simpleboard.json";
			break;
		default:
			filename = "simpleboard.json";
		}
		*/
		
		// Load the BoardEntityLoader.
		BoardEntityLoader loadedBoard = new BoardEntityLoader("simpleBoard.json", stage, this);
		return loadedBoard;
		
	}

		
	// Loads the Game screen for the given level by 'DungeonControllerLoader'
	public void loadGameScreen (Stage stage, Game game) throws IOException {
			
		// Get the correct Json file for the current level.
		BoardEntityLoader boardLoader = loadJsonBoard(stage, game);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		boardcontroller = boardLoader.loadController(game);
		// Game is now up-to date
		loader.setController(boardcontroller);
			
		
		Parent root = loader.load();
		scene = new Scene(root, 600, 550);
		scene.getStylesheets().add(getClass().getResource("StartScreenStyleSheet.css").toExternalForm());
		scene.setOnKeyPressed(this);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		
	}





	
	
}
