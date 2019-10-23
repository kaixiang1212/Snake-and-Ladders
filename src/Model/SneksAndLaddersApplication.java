package Sneks_and_Ladders;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class SneksAndLaddersApplication extends Application {

	@Override
    public void start(Stage primaryStage) throws IOException {
    		
    	StartGameScreen startScreen = new StartGameScreen(primaryStage);
    	
        startScreen.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

    