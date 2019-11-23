package Controller;

import java.io.IOException;
import java.text.DecimalFormat;

import Model.Player;
import Model.Stats;
import View.StartGameScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class StatsController {
	
	@FXML
	private Label title;
	@FXML
	private Text message;
	@FXML
    private Button backButton;
	@FXML
    private Button leaderboardsButton;
	@FXML
    private Pane searchPane;
	@FXML
    private Button searchButton;
	@FXML
    private TextField nameTextbox;
	@FXML
    private Pane statsPane;
	@FXML
    private Text gamesWon;
	@FXML
    private Text gamesPlayed;
	@FXML
    private Text winRatio;
	@FXML
    private Text itemsUsed;
	@FXML
    private Text avgItemsUsed;
	@FXML
    private Text avgItemsCollected;
	@FXML
    private Text numDiceRolled;
	@FXML
    private Text avgDiceRoll;
	@FXML
    private Text avgFinishTile;
	@FXML
    private Text ladders;
	@FXML
    private Text snakes;
	@FXML
    private Text timesPoisoned;
	
    public StatsController(){
    	MusicController.initUI();
    }
    
    @FXML
    public void searchButtonClicked() {
    	MusicController.playNext();
    	if(nameTextbox.getText().trim().isEmpty()) {
    		message.setText("Player name cannot be empty.");
    		return;
    	}
    	message.setText("");
    	Stats stats = new Player(nameTextbox.getText(), 0, 0, 0).getStats();
    	DecimalFormat df = new DecimalFormat("0.00");
    	
    	searchPane.setVisible(false);
    	gamesWon.setText(Integer.toString(stats.getGamesWon()));
    	gamesPlayed.setText(Integer.toString(stats.getGamesPlayed()));
    	winRatio.setText(df.format(stats.getWinRatio()));
    	itemsUsed.setText(Integer.toString(stats.getItemsUsed()));
    	avgItemsUsed.setText(df.format(stats.getAvgItemsUsed()));
    	avgItemsCollected.setText(df.format(stats.getAvgItemsCollected()));
    	
    	numDiceRolled.setText(Integer.toString(stats.getNumDiceRolled()));
    	avgDiceRoll.setText(df.format(stats.getAvgDiceRoll()));
    	avgFinishTile.setText(Integer.toString((int)stats.getAvgFinishTile()));
    	
    	ladders.setText(Integer.toString(stats.getLadders()));
    	snakes.setText(Integer.toString(stats.getSnakes()));
    	timesPoisoned.setText(Integer.toString(stats.getTimesPoisoned()));
    	statsPane.setVisible(true);
    	title.setText(nameTextbox.getText().trim() + "'s Statistics");
    }
    
    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
    	MusicController.playBack();
    	if(statsPane.isVisible()) {
    		statsPane.setVisible(false);
    		title.setText("Statistics");
    		nameTextbox.clear();
    		searchPane.setVisible(true);
    	} else {
    		StartGameScreen.start();
    	}
    }
    
    @FXML
    public void leaderboardsButtonClicked(ActionEvent event) throws IOException {
    	MusicController.playNext();
    }
}
