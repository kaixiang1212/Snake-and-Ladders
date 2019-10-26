package View;

import Controller.*;
import Model.*;

import java.io.IOException;

import org.json.JSONException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class GameScreen implements EventHandler<KeyEvent> {
	private final int HEIGHT = 1000;
	private final int WIDTH = 1000;
	
	private Stage stage;
	private String title;
	private Scene scene;
	private BoardController boardcontroller;
	private GameEngine engine;

	public GameScreen(Stage s) {
		this.stage = s;
		this.title = "Sneks & Ladders";
		//this.engine = engine;
	}

	public void start() throws IOException, JSONException {
		//this.stage = s;
		loadGameScreen(this.stage, this.engine);
	}
	
	@Override
	public void handle(KeyEvent event) {
		boardcontroller.handleKeyPress(event);
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
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View/BoardView.fxml"));
		boardcontroller = boardLoader.loadController();
		//boardcontroller.initialize();
		// Game is now up-to date
		loader.setController(boardcontroller);
		
		Parent root = loader.load();
		scene = new Scene(root, WIDTH, HEIGHT);
		//scene.getStylesheets().add(getClass().getResource("StartScreenStyleSheet.css").toExternalForm());
		scene.setOnKeyPressed(this);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return WIDTH;
	}





	
	
}
