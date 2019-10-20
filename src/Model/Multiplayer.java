package Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Multiplayer {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerNum;
    private int playerNum;
    private Board board;
    private Stage stage;

    public Multiplayer(Stage stage, Board board){
        players = new ArrayList<>();
        this.stage = stage;
        this.board = board;
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Model/dice.fxml"));
    }

    /**
     * Add a player into the game
     * @param player A player object to be added into the game
     */
    public void addPlayer(Player player){
        if (currentPlayer == null) {
            currentPlayerNum = 0;
            currentPlayer = player;
        }
        players.add(player);
        playerNum++;
    }

    /**
     * Get the number of players in the game
     * @return number of players
     */
    public int getPlayerNum(){
        return playerNum;
    }

    /**
     * Get current player's position
     * @return current player's position
     */
    public int getPosition(){
        return currentPlayer.getPosition();
    }

    /**
     * Update current player's position
     * @param position player's current position
     */
    public void updatePosition(int position){
        currentPlayer.setPosition(position);
    }

    /**
     * Invoked when dice button is clicked
     */
    public int rollDice(){
        return board.rollDice(currentPlayer);
    }

    /**
     * Get current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Iterate over the next player
     */
    public void nextPlayer(){
        currentPlayerNum = (currentPlayerNum + 1) % playerNum;
        currentPlayer = players.get(currentPlayerNum);
    }

    /**
     * JavaFx stuff
     */
    private FXMLLoader fxmlLoader;

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
}
