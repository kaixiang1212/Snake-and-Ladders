package View;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class SneksAndLaddersApplication extends Application {
	
	@Override
    public void start(Stage primaryStage) throws IOException {
    		
    	new StartGameScreen(primaryStage);

        StartGameScreen.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}