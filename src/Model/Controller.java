package Model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

    @FXML
    public Button rollButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text result;
    private Stage stage;
    private Dice dice;

    public Controller(){

    }

    public Controller(Stage stage, Dice dice){
        this.dice = dice;
        this.stage = stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void rollButtonClicked(){
        result.setText(String.valueOf(dice.roll()));
    }
}
