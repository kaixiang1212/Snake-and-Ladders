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
	private final int HEIGHT = 800;
	private final int WIDTH = 800;

	private final int diceWidth = 200;
	
	private Stage stage;
	private String title;
	private Scene scene;
	private BoardController boardController;
	private GameEngine engine;

	public GameScreen(Stage s) {
		this.stage = s;
		this.title = "Sneks & Ladders";
	}

	public void start() throws IOException, JSONException {
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
		BoardEntityLoader loadedBoard = new BoardEntityLoader("animatedBoard.json", stage, this, engine);
		return loadedBoard;
		
	}
		
	
	public void loadGameScreen (Stage stage, GameEngine game) throws IOException, JSONException {
		// Get the correct Json file for the current level.
		BoardEntityLoader boardLoader = loadJsonBoard(stage, game);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/altBoardView.fxml"));
		
		Parent root = loader.load();
		scene = new Scene(root, WIDTH + diceWidth, HEIGHT);
		// Configure Board Controller
		boardController = loader.getController();
		boardLoader.configBoardController(boardController);
		boardController.init();
		
		// Create a GifController to manage the gifs once the boar has been loaded.
		GifController gifcontroller = new GifController(boardController);
		
		// Add the Gifcontroller to the gameEngine
		game.setGifcontroller(gifcontroller);
		
		
		//System.out.println("" + boardController.gifladder1);
		
		
		//scene.getStylesheets().add(getClass().getResource("StartScreenStyleSheet.css").toExternalForm());
		//stage.setResizable(true);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
//		double ratio = scene.getWindow().getHeight()/scene.getWindow().getWidth();
//		stage.minHeightProperty().bind(stage.widthProperty().multiply(ratio));
//		stage.maxHeightProperty().bind(stage.widthProperty().multiply(ratio));
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public double getSceneWidth() {
		return scene.getWidth();
	}
	
	public double getSceneHeight() {
		return scene.getHeight();
	}






	
	
}
