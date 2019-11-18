package View;

import Controller.*;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// java class for startscreen

public class StartGameScreen {

	private static Stage stage;
	private static String title;
	
	public StartGameScreen (Stage s) throws IOException {
		stage = s;
		title = "Start Screen";
		stage.setResizable(false);
	}
	
	public static void start() throws IOException {
		Parent root = FXMLLoader.load(StartGameScreen.class.getResource("fxml/StartGameScreen.fxml"));
		Scene scene = new Scene(root, 800, 800);
		stage.sizeToScene();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public static Stage getStage() {
		return stage;
	}

}
