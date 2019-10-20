package Model;

import javafx.application.Application;
import javafx.stage.Stage;

public class DiceFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setHeight(300);
        primaryStage.setWidth(300);

        Multiplayer multiplayer= new Multiplayer(primaryStage, new Board());
        multiplayer.addPlayer(new Player("Player 1"));
        multiplayer.addPlayer(new Player("Player 2"));
        multiplayer.start();
    }
}
