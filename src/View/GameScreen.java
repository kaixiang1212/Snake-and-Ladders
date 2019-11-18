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
		String filename;
		switch (GameEngine.getBoardType()) {
		case 1:
			filename = "fxml/altBoardView.fxml";
			break;
		case 2:
			filename = "fxml/altBoardViewPlain.fxml";
			break;
		case 3:
			filename = "fxml/altBoardViewSnakeless.fxml";
			break;
		case 4:
			filename = "fxml/altBoardViewLadderless.fxml";
			break;
		default:
			filename = "fxml/altBoardView.fxml";
		}
		FXMLLoader loader = new FXMLLoader(GameScreen.class.getResource(filename));
		
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
		
		String filename;
		int currentBoard = GameEngine.getBoardType();
		
		switch (currentBoard) {
			case 1:
				filename = "animatedBoard.json";
				break;
			case 2:
				filename = "animatedBoardPlain.json";
				break;
			case 3:
				filename = "animatedBoardSnakeless.json";
				break;
			case 4:
				filename = "animatedBoardLadderless.json";
				break;
			default:
				filename = "animatedBoard.json";
		}
		
		// Load the BoardEntityLoader.
		
		new BoardEntityLoader(filename, stage);

	}
		
	/*
	public void loadGameScreen (Stage stage, GameEngine game) throws IOException, JSONException {
		// Get the correct Json file for the current level.
		loadJsonBoard();
		String filename;
		switch (game.getBoardType()) {
		case 1:
			filename = "altBoardView.fxml";
			break;
		case 2:
			filename = "altBoardViewPlain.fxml";
			break;
		case 3:
			filename = "altBoardViewSnakeless.fxml";
			break;
		case 4:
			filename = "altBoardViewLadderless.fxml";
			break;
		default:
			filename = "altBoardView.fxml";
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/" + filename));
		
		Parent root = loader.load();
		scene = new Scene(root, WIDTH + diceWidth, HEIGHT);
		// Configure Board Controller
		boardController = loader.getController();
		boardLoader.configBoardController(boardController);
		boardController.init();
		
		// Create a GifController to manage the gifs once the board has been loaded.
		GifController gifcontroller = new GifController(boardController);
		

		
		
		//System.out.println("" + boardController.gifladder1);
		
		
		//scene.getStylesheets().add(getClass().getResource("StartScreenStyleSheet.css").toExternalForm());
		//stage.setResizable(true);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
//		double ratio = scene.getWindow().getHeight()/scene.getWindow().getWidth();
//		stage.minHeightProperty().bind(stage.widthProperty().multiply(ratio));
//		stage.maxHeightProperty().bind(stage.widthProperty().multiply(ratio));
    }*/

	
	
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
