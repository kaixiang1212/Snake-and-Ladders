package View;

import Controller.*;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


// java class for startscreen

public class StartGameScreen {

	private Stage stage;
	private String title;
	private StartGameScreenController controller;
	private Scene scene;
	
	
	public StartGameScreen (Stage stage) throws IOException {
		this.stage = stage;
		this.title = "Start Screen";
		stage.setResizable(false);
		if(!stage.isShowing())
			stage.initStyle(StageStyle.UTILITY);
		controller = new StartGameScreenController(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/StartGameScreen.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		scene = new Scene(root, 800, 800);
	}
	
	public void start() {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public StartGameScreenController getController() {
		return controller;
	}

}
