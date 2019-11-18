package View;

import Controller.*;

import java.io.IOException;

import org.json.JSONException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameScreen {
	private static final int HEIGHT = 800;
	private static final int WIDTH = 800;

	private static final int diceWidth = 200;
	
	private static Stage stage;
	private static String title;
	private static Scene scene;
	private static BoardController boardController;

	public GameScreen(Stage s) {
		stage = s;
		title = "Sneks & Ladders";
	}

	public static void start() throws IOException, JSONException {
		loadGameScreen();
	}

	public static void loadGameScreen() throws IOException, JSONException {
		// Get the correct Json file for the current level.
		loadJsonBoard();
		FXMLLoader loader = new FXMLLoader(GameScreen.class.getResource("fxml/altBoardView.fxml"));
		
		Parent root = loader.load();
		scene = new Scene(root, WIDTH + diceWidth, HEIGHT);
		
		// Configure Board Controller
		boardController = loader.getController();
		BoardEntityLoader.configBoardController(boardController);
		boardController.init();

		// Create a GifController to manage the gifs once the boar has been loaded.
		new GifController(boardController);
		
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	// NOTE: Probably the part where you choose the Board
	public static void loadJsonBoard() throws IOException, JSONException {
		
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
		new BoardEntityLoader("animatedBoard.json", stage);
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
	
	public static double getSceneWidth() {
		return scene.getWidth();
	}
	
	public static double getSceneHeight() {
		return scene.getHeight();
	}






	
	
}
