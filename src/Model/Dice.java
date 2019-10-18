package Model;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Dice {

    private Random r;
    private Stage stage;
    private FXMLLoader fxmlLoader;

    public Dice(Stage stage){
        this.r = new Random();
        this.stage = stage;
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Model/dice.fxml"));
    }

    public void start(){
        stage.setTitle("Dice");
        System.out.println(getClass().getClassLoader().getResource("Model/dice.fxml"));
        fxmlLoader.setController(new Controller(stage, this));
        Controller con = fxmlLoader.getController();
        System.out.println(con);
        con.setStage(stage);
        try {
            Parent root;
            root = fxmlLoader.load();
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Roll a dice and get a random number between 1 and 6
     * @return a random number between 1 and 6
     */
    public int roll(){
        return r.nextInt(6) + 1;
    }

}
