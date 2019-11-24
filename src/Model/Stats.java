package Model;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

public class Stats {
	
	String playerName;
	private int gamesPlayed;
	private int gamesWon;
	private float winRatio;				// calculated
	private int totalFinishTile;
	private float avgFinishTile;		// calculated
	private int itemsUsed;
	private float avgItemsUsed;			// calculated
	private int itemsCollected;
	private float avgItemsCollected;	// calculated
	private int numDiceRolled;
	private float avgNumRolls;			// calculated
	private int totalDiceResults;
	private float avgDiceRoll;			// calculated
	private int ladders;
	private int snakes;
	private int timesPoisoned;
	
	private static JSONObject statsFile;
	
	public Stats(String playerName) throws FileNotFoundException, JSONException {
		this.playerName = playerName;
		if(!importStats()) {
			gamesPlayed = 0;
			gamesWon = 0;
			winRatio = 0;
			totalFinishTile = 0;
			avgFinishTile = 0;
			itemsUsed = 0;
			itemsCollected = 0;
			avgItemsCollected = 0;
			avgItemsUsed = 0;
			numDiceRolled = 0;
			avgNumRolls = 0;
			totalDiceResults = 0;
			avgDiceRoll = 0;
			ladders = 0;
			snakes = 0;
			timesPoisoned = 0;
		}
	}
	
	public boolean importStats() throws FileNotFoundException, JSONException {
		importStatsFile();
        JSONArray statsArray  = statsFile.getJSONArray("players");
        int i = 0;
        JSONObject playerStats;
		for(i = 0; i < statsArray.length(); i++) {
			playerStats = statsArray.getJSONObject(i);
			if(playerStats.getString("playerName").equals(playerName)) {
				setGamesPlayed(playerStats.getInt("gamesPlayed"));
				setGamesWon(playerStats.getInt("gamesWon"));
				setTotalFinishTile(playerStats.getInt("totalFinishTile"));
				setItemsUsed(playerStats.getInt("itemsUsed")); 
				setItemsCollected(playerStats.getInt("itemsCollected"));
				setNumDiceRolled(playerStats.getInt("numDiceRolled"));
				setTotalDiceResults(playerStats.getInt("totalDiceResults"));
				setSnakes(playerStats.getInt("snakes"));
				setLadders(playerStats.getInt("ladders"));
				setTimesPoisoned(playerStats.getInt("timesPoisoned"));
				return true;
			}
		}
		
		return false;
	}
	
	public static void importStatsFile() throws FileNotFoundException, JSONException {
		String path = Stats.class.getClassLoader().getResource("boards/" + "stats.json").getPath().replaceAll("%20", " ");
    	assert(!path.isEmpty());
    	statsFile = new JSONObject(new JSONTokener(new FileReader(path)));
	}
	
	public static JSONObject getStatsFile() {
		return statsFile;
	}
	
	public void exportStats() throws JSONException, IOException {
		int i = 0;
		for(i = 0; i < statsFile.getJSONArray("players").length(); i++) {
			JSONObject obj = statsFile.getJSONArray("players").getJSONObject(i);
			if(obj.getString("playerName").equals(playerName)) {
				break;
			}
		}
		JSONObject playerStats = new JSONObject();
		playerStats.put("playerName", playerName);
		playerStats.put("gamesPlayed", gamesPlayed);
		playerStats.put("gamesWon", gamesWon);
		playerStats.put("totalFinishTile", totalFinishTile);
		playerStats.put("itemsUsed", itemsUsed);
		playerStats.put("itemsCollected", itemsCollected);
		playerStats.put("numDiceRolled", numDiceRolled);
		playerStats.put("totalDiceResults", totalDiceResults);
		playerStats.put("snakes", snakes);
		playerStats.put("ladders", ladders);
		playerStats.put("timesPoisoned", timesPoisoned);
		if(i < statsFile.getJSONArray("players").length()) {
			statsFile.getJSONArray("players").put(i, playerStats);
		} else {
			statsFile.getJSONArray("players").put(playerStats);
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/boards/stats.json"));
		writer.write(statsFile.toString(2));
		writer.flush();
		writer.close();
    }

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
		winRatio = (float)gamesWon/(float)gamesPlayed;
		avgFinishTile = (float)totalFinishTile/(float)gamesPlayed;
		avgItemsCollected = (float)itemsCollected/(float)gamesPlayed;
		avgItemsUsed = (float)itemsUsed/(float)gamesPlayed;
		avgNumRolls = (float)numDiceRolled/(float)gamesPlayed;
	}
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
		winRatio = (float)gamesWon/(float)gamesPlayed;
	}
	
	public int getGamesWon() {
		return gamesWon;
	}
	
	public float getWinRatio() {
		return winRatio;
	}
	
	public void setTotalFinishTile(int totalFinishTile) {
		this.totalFinishTile = totalFinishTile;
		avgFinishTile = (float)totalFinishTile/(float)gamesPlayed;
	}
	
	public int getTotalFinishTile() {
		return this.totalFinishTile;
	}
	
	public float getAvgFinishTile () {
		return avgFinishTile;
	}
	
	public void setItemsUsed(int numItemsUsed) {
		itemsUsed = numItemsUsed;
		avgItemsUsed = (float)itemsUsed/(float)gamesPlayed;
	}
	
	public int getItemsUsed() {
		return itemsUsed;
	}
	
	public float getAvgItemsUsed() {
		return avgItemsUsed;
	}
	
	public void setItemsCollected(int numItemsCollected) {
		itemsCollected = numItemsCollected;
		avgItemsCollected = (float)itemsCollected/(float)gamesPlayed;
	}
	
	public int getItemsCollected() {
		return itemsCollected;
	}
	
	public float getAvgItemsCollected() {
		return avgItemsCollected;
	}
	
	public void setNumDiceRolled(int numDiceRolled) {
		this.numDiceRolled = numDiceRolled;
		avgDiceRoll = (float)totalDiceResults/(float)this.numDiceRolled;
		avgNumRolls = (float)this.numDiceRolled/(float)gamesPlayed;
	}
	
	public int getNumDiceRolled() {
		return numDiceRolled;
	}
	
	public void setTotalDiceResults(int totalDiceResults) {
		this.totalDiceResults = totalDiceResults;
		avgDiceRoll = (float)totalDiceResults/(float)this.numDiceRolled;
	}
	
	public int getTotalDiceResults() {
		return totalDiceResults;
	}
	
	public float getAvgDiceRoll() {
		return avgDiceRoll;
	}
	
	public float getAvgNumRolls() {
		return avgNumRolls;
	}
	
	public void incrementGamesPlayed(int i) {
		setGamesPlayed(getGamesPlayed()+i);
	}
	
	public void incrementGamesWon(int i) {
		setGamesWon(getGamesWon()+i);
	}
	public void incrementTotalFinishTile(int i) {
		setTotalFinishTile(getTotalFinishTile()+i);
	}
	public void incrementItemsUsed(int i) {
		setItemsUsed(getItemsUsed()+i);
	}
	public void incrementItemsCollected(int i) {
		setItemsCollected(getItemsCollected()+i);
	}
	public void incrementNumDiceRolled(int i) {
		setNumDiceRolled(getNumDiceRolled()+i);
	}
	public void incrementTotalDiceResults(int i) {
		setTotalDiceResults(getTotalDiceResults()+i);
	}
	
	public void printStats() {
		System.out.print("{" +
				"\n  \"playerName\": " + playerName +
				"\n  \"gamesWon\": " + gamesWon +
				"\n  \"gamesPlayed\": " + gamesPlayed +
				"\n  \"winRatio\": " + winRatio +
				"\n  \"avgFinishTile\": " + (int) avgFinishTile +
				"\n  \"totalFinishTile\": " + totalFinishTile +
				"\n  \"avgItemsCollected\": " + (int) avgItemsCollected + 
				"\n  \"avgItemsUsed\": " + (int) avgItemsUsed +
				"\n  \"itemsCollected\": " + itemsCollected + 
				"\n  \"itemsUsed\": " + itemsUsed +
				"\n  \"avgNumRolls:\" " + avgNumRolls +
				"\n  \"avgDiceRoll\": " + avgDiceRoll +
				"\n  \"numDiceRolled\": " + numDiceRolled +
				"\n  \"totalDiceResults\": " + totalDiceResults + 
				"\n}");
	}
	
	public void setSnakes(int n) {
		snakes = n;
	}
	
	public int getSnakes() {
		return snakes;
	}
	
	public void incrementSnakes() {
		snakes++;
	}
	
	public void setLadders(int n) {
		ladders = n;
	}
	
	public int getLadders() {
		return ladders;
	}
	
	public void incrementLadders() {
		ladders++;
	}
	
	public void setTimesPoisoned(int n) {
		timesPoisoned = n;
	}
	
	public int getTimesPoisoned() {
		return timesPoisoned;
	}
	
	public void incrementTimesPoisoned() {
		timesPoisoned++;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
}
