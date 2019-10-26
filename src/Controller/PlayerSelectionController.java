package Controller;

import java.io.IOException;

import org.json.JSONException;

import Model.GameEngine;
import Model.Player;
import View.GameScreen;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerSelectionController {

    @FXML
    private Button addPlayerButton;
    @FXML
    private FlowPane flowPane;

    private Stage stage;

    private int playerCount = 1;

    private char[] token = {'@', '$', '%', '#'};

    public PlayerSelectionController(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void addPlayerButtonClicked(){
        if (flowPane.getChildren().size() >= 5) return;
        playerCount++;
        VBox vBox = new VBox();
        TextField textField = new TextField();
        Button removeButton = new Button();
        removeButton.setText("Remove Player");
        textField.setText("Player " + playerCount);
        vBox.getChildren().add(textField);
        vBox.getChildren().add(removeButton);
        removeButton.setOnAction(actionEvent -> removeButtonClicked(vBox));
        this.flowPane.getChildren().remove(addPlayerButton);
        this.flowPane.getChildren().add(vBox);
        if (flowPane.getChildren().size() < 4) {
            this.flowPane.getChildren().add(addPlayerButton);
        }
        textField.setAlignment(Pos.CENTER);
        vBox.setPrefSize(100, 10);
        VBox.setMargin(textField, new Insets(70,0,0,0));
        VBox.setMargin(removeButton, new Insets(10,0,0,0));
        FlowPane.setMargin(vBox, new Insets(0,10,0,10));
    }

    public void removeButtonClicked(VBox vBox){
        flowPane.getChildren().remove(vBox);
        if (!flowPane.getChildren().contains(addPlayerButton)){
            flowPane.getChildren().add(addPlayerButton);
        }
        playerCount--;
    }

    @FXML
    public void createGameButtonClicked() throws IOException, JSONException{
        GameEngine engine = new GameEngine();
        //System.out.println(flowPane.getChildren());
        int tokenIndex = 0;
        for (Node node : flowPane.getChildren()){
            if (node instanceof VBox){
                for (Node node1 : ((VBox) node).getChildren()){
                    if (node1 instanceof TextField) {
                        String playerName = ((TextField) node1).getText();
                        engine.addPlayer(new Player(playerName, token[tokenIndex], 1, 1));
                        tokenIndex++;
                    }
                }
            }
        }
        GameScreen game = new GameScreen(stage);
        game.setEngine(engine);
        game.start();
    }
}
