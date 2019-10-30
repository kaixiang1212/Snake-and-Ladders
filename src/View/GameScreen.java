package View;

import Controller.*;
import Model.*;

import java.io.IOException;

import org.json.JSONException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameScreen {
	private final int HEIGHT = 750;
	private final int WIDTH = 750;

	private final int diceWidth = 200;
	
	private Stage stage;
	private String title;
	private Scene scene;
	private BoardController boardcontroller;
	private GameEngine engine;

	public GameScreen(Stage s) {
		this.stage = s;
		this.title = "Sneks & Ladders";
	}

	public void start() throws IOException, JSONException {
		//this.stage = s;
		loadGameScreen(this.stage, this.engine);
	}

	public GameEngine getEngine() {
		return engine;
	}
	
	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	// NOTE--> Probably the part where you choose the Board
	public BoardEntityLoader loadJsonBoard(Stage stage, GameEngine game) throws IOException, JSONException {
		
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
		BoardEntityLoader loadedBoard = new BoardEntityLoader("simpleBoard.json", stage, this, engine);
		return loadedBoard;
		
	}
		
	// Loads the Game screen for the given level by 'DungeonControllerLoader'
	public void loadGameScreen (Stage stage, GameEngine game) throws IOException, JSONException {

		// Get the correct Json file for the current level.
		BoardEntityLoader boardLoader = loadJsonBoard(stage, game);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/BoardView.fxml"));
		
		Parent root = loader.load();
		// Configure Board Controller
		BoardController boardController = loader.getController();
		boardLoader.configBoardController(boardController);
		boardController.init();

		scene = new Scene(root, WIDTH + diceWidth, HEIGHT);
		//scene.getStylesheets().add(getClass().getResource("StartScreenStyleSheet.css").toExternalForm());
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}





	
	
}
