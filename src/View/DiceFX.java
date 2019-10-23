package View;

import java.io.IOException;

import Controller.DiceController;
import Model.GameEngine;
import Model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DiceFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setHeight(300);
        primaryStage.setWidth(300);

        GameEngine engine = new GameEngine();
        engine.addPlayer(new Player("Player 1"));
        engine.addPlayer(new Player("Player 2"));

        primaryStage.setTitle("Dice");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/dice.fxml"));
        fxmlLoader.setController(new DiceController(primaryStage, engine));
        try {
            Parent root;
            root = fxmlLoader.load();
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
