package View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StatsScreen {
	private static String screenTitle;

	public StatsScreen () {
	      screenTitle = "Statistics & Leaderboards";
	}
	
    public static void start() {
        try {
        	Parent root = FXMLLoader.load(HelpGameScreen.class.getResource("fxml/Statistics.fxml"));
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
