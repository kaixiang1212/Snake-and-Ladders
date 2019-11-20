package View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpGameScreen {
	
	private static String screenTitle;

	public HelpGameScreen () {
	      screenTitle = "Help Screen";
	}
	
    public static void start() {
        try {
        	Parent root = FXMLLoader.load(HelpGameScreen.class.getResource("fxml/Help.fxml"));
        	Scene sc = new Scene(root);
            Stage stage = StartGameScreen.getStage();
            stage.setTitle(screenTitle);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
}
