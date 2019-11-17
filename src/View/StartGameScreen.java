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
	private static StartGameScreenController controller;
	private static Scene scene;
	
	
	public StartGameScreen (Stage s) throws IOException {
		stage = s;
		title = "Start Screen";
		stage.setResizable(false);
		//if(!stage.isShowing())
			//stage.initStyle(StageStyle.DECORATED);
		controller = new StartGameScreenController(s);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/StartGameScreen.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		scene = new Scene(root, 800, 800);
		stage.sizeToScene();
	}
	
	public static void start() {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public static StartGameScreenController getController() {
		return controller;
	}

}
