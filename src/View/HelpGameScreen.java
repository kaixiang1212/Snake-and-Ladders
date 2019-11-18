package View;

import java.io.IOException;

import Controller.HelpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpGameScreen {
	
	private static String screenTitle;
	private static Stage stage;
	private static FXMLLoader fxmlLoader;

	public HelpGameScreen (Stage s) {
	      stage = s;
	      screenTitle = "help screen";
	      fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Help.fxml"));
	}
	
    public static void start() {
        stage.setTitle(screenTitle);
        try {
            Parent root = fxmlLoader.load();
            ((HelpController)fxmlLoader.getController()).setStage(stage);

            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
}
