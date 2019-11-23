package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.Player;
import Model.Stats;
import View.StartGameScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML
	private Pane leaderboardsPane;
	@FXML
	private TableView tableView;
	@FXML
	private TableColumn<String, Stats> playerNameColumn;
	@FXML
	private TableColumn<Integer, Stats> gamesWonColumn;
	@FXML
	private TableColumn<Integer, Stats> gamesPlayedColumn;
	@FXML
	private TableColumn<Float, Stats> winRatioColumn;
	
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
    	if(leaderboardsPane.isVisible()) {
    		toggleLeaderboards();
    	} else if(statsPane.isVisible()) {
    		statsPane.setVisible(false);
    		title.setText("Statistics");
    		nameTextbox.clear();
    		searchPane.setVisible(true);
    	} else {
    		StartGameScreen.start();
    	}
    }
    
    @FXML
    public void leaderboardsButtonClicked() throws IOException {
    	MusicController.playSwitch();
    	toggleLeaderboards();
    }
    
    public void toggleLeaderboards() {
    	if(!leaderboardsPane.isVisible()) {
	    	leaderboardsPane.setVisible(true);
	    	try {
				getLeaderboards();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	tableView.getSortOrder().add(winRatioColumn);
	    	leaderboardsButton.setText("Statistics");
	    	title.setText("Leaderboards");
    	} else {
	    	leaderboardsPane.setVisible(false);
	    	leaderboardsButton.setText("Leaderboards");
	    	if(statsPane.isVisible()) {
	        	title.setText(nameTextbox.getText().trim() + "'s Statistics");
	    	} else {
	        	title.setText("Statistics");
	    	}
    	}
    }
    
    public void getLeaderboards() throws FileNotFoundException, JSONException  {
    	tableView.getItems().clear();
    	playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
    	gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
    	gamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
    	winRatioColumn.setCellValueFactory(new PropertyValueFactory<>("winRatio"));
    	playerNameColumn.setResizable(false);
    	gamesWonColumn.setResizable(false);
    	gamesPlayedColumn.setResizable(false);
    	winRatioColumn.setResizable(false);
		Stats.importStatsFile();
    	JSONObject statsFile = Stats.getStatsFile();
    	JSONArray statsArray  = statsFile.getJSONArray("players"); 
        JSONObject playerStats;
        int i = 0;
		for(i = 0; i < statsArray.length(); i++) {
			playerStats = statsArray.getJSONObject(i);
			tableView.getItems().add(new Player(playerStats.getString("playerName"), 0, 0, 0).getStats());
		}
    }
}
