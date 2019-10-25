package View;

import java.io.IOException;

import Controller.GameScreenController;
import Model.GameEngine;
import Model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameScreen {

    private GameEngine engine;
    private Stage stage;

    public GameScreen(Stage stage){
        this.stage = stage;
    }

    public void setEngine(GameEngine engine){
        this.engine = engine;
    }

    public void start(Stage stage) {

        if (engine == null){
            GameEngine engine = new GameEngine();
            engine.addPlayer(new Player("Player 1", '@'));
            engine.addPlayer(new Player("Player 2", '$'));
        }

        this.stage.setTitle("Game Screen");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/gameScreen.fxml"));
        fxmlLoader.setController(new GameScreenController(this.stage, engine));
        try {
            Parent root;
            root = fxmlLoader.load();
            Scene sc = new Scene(root);
            this.stage.setScene(sc);
            this.stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
	