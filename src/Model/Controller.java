package Model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

    @FXML
    public Button rollButton;
    @FXML
    private ImageView diceImage;
    @FXML
    private Text text;

    private Stage stage;
    private Multiplayer players;

    public Controller(Stage stage, Multiplayer players){
        this.players = players;
        this.stage = stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void rollButtonClicked(){
        rollButton.setDisable(true);
        Image image;
        int result = players.rollDice();
        switch (result){
            case 1:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1.png")));
                break;
            case 2:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2.png")));
                break;
            case 3:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3.png")));
                break;
            case 4:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4.png")));
                break;
            case 5:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5.png")));
                break;
            case 6:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6.png")));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }
        diceImage.setImage(image);
        text.setText(String.valueOf(result));
        rollButton.setDisable(false);
        players.nextPlayer();
    }
}
