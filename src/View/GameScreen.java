package View;

import Controller.*;
import Model.Board.BoardType;
import Model.GameEngine;

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

	private static String title;
	private static BoardType boardType;
	
	public GameScreen(BoardType type) {
		title = "Sneks & Ladders";
		boardType = type;
	}

	public static void start() throws IOException, JSONException {
		loadGameScreen();
	}

	public static void loadGameScreen() throws IOException, JSONException {
		// Get the correct Json file for the current level.
		loadJsonBoard();
		String filename;
		switch (boardType) {
		case DEFAULT:
			filename = "fxml/altBoardView.fxml";
			break;
		case PLAIN:
			filename = "fxml/altBoardViewPlain.fxml";
			break;
		case SNAKELESS:
			filename = "fxml/altBoardViewSnakeless.fxml";
			break;
		case LADDERLESS:
			filename = "fxml/altBoardViewLadderless.fxml";
			break;
		default:
			filename = "fxml/altBoardView.fxml";
		}
		// Configure Board Controller
		FXMLLoader loader = new FXMLLoader(GameScreen.class.getResource(filename));
		Parent root = loader.load();
		BoardController boardController = loader.getController();
		BoardEntityLoader.configBoardController(boardController, boardType);
		boardController.init();
		
		Scene scene = new Scene(root, WIDTH + diceWidth, HEIGHT);
		Stage stage = StartGameScreen.getStage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	// NOTE: Probably the part where you choose the Board
	public static void loadJsonBoard() throws IOException, JSONException {		
		String filename;
		
		switch (boardType) {
			case DEFAULT:
				filename = "animatedBoard.json";
				break;
			case PLAIN:
				filename = "animatedBoardPlain.json";
				break;
			case SNAKELESS:
				filename = "animatedBoardSnakeless.json";
				break;
			case LADDERLESS:
				filename = "animatedBoardLadderless.json";
				break;
			default:
				filename = "animatedBoard.json";
		}
		
		// Load the BoardEntityLoader.	
		new BoardEntityLoader(filename);

	}

	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
	
	public static double getSceneWidth() {
		return StartGameScreen.getStage().getScene().getWidth();
	}
	
	public static double getSceneHeight() {
		return StartGameScreen.getStage().getScene().getHeight();
	}
	
}
