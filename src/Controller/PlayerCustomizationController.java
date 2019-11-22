package Controller;

import java.io.IOException;
import java.util.ArrayList;

import Controller.Networking.Server;
import View.PlayerNumSelectionScreen;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONException;

import Model.GameEngine;
import Model.Player;
import View.GameScreen;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PlayerCustomizationController {
	
	private final int maxTokens = 8;
	
    @FXML
    private FlowPane flowPane;

    private int playerCount = 1;
    private int boardNum;

    private ArrayList<Integer> token;
    private ArrayList<Integer> availableToken;

    private Server server;


    public PlayerCustomizationController(){
        token = new ArrayList<>();
        token.add(0,0);
        token.add(1,-1);
        token.add(2,-1);
        token.add(3,-1);
        availableToken = new ArrayList<>();
        MusicController.initUI();
        server = new Server();
        server.setCustomisableController(this);
        server.start();
        server.onPlayerSelection();

    }

    /**
     * Render a customisation screen for
     * given number of player
     * @param numPlayer number of player
     */
    public void setPlayers(int numPlayer){
        while (playerCount != numPlayer){
            addPlayer();
            playerCount++;
        }
        server.setPlayers(numPlayer);
    }
    
    public void setBoard(int b) { boardNum = b; }

    /**
     * Add player
     */
    private void addPlayer(){
        int playerNum = nextToken();
        VBox vBox = new VBox();
        TextField textField = new TextField();
        ImageView imageView = new ImageView();
        vBox.getChildren().add(imageView);
        imageView.setImage(getImage(playerNum));
        imageView.setOnMouseClicked(mouseEvent -> imageClicked(imageView));
        textField.setText("Player " + (playerCount+1));
        vBox.getChildren().add(textField);
        this.flowPane.getChildren().add(vBox);
        textField.setAlignment(Pos.CENTER);
        textField.setFont(new Font(16));
        vBox.setAlignment(Pos.CENTER);
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        vBox.setPrefSize(100, 10);
        VBox.setMargin(textField, new Insets(10,0,0,0));
        FlowPane.setMargin(vBox, new Insets(10,10,0,10));
        setPlayerToken(playerCount, playerNum);   
    }

    /**
     * Generate a list of available tokens
     * and Return next available token
     * @return next token
     */
    private int nextToken(){
        if (!availableToken.isEmpty()) return availableToken.remove(0);
        for (int i=0; i < maxTokens; i++){
            if (!token.contains(i))
            	availableToken.add(i);
        }
        return availableToken.remove(0);
    }

    /**
     * Given a token
     * return corresponding image
     * @param token token of image
     * @return Image
     */
    private Image getImage(int token){
        return new Image(String.valueOf(getClass().getClassLoader().getResource("asset/token" + token + ".png")));
    }

    /**
     * Set given token to given player
     * @param playerIndex Player index (0-3)
     * @param playerToken token index (0-4)
     */
    private void setPlayerToken(int playerIndex, int playerToken){
        token.set(playerIndex, playerToken);
    }

    @FXML
    public void imageClicked1(){
    	MusicController.clear();
        for(Node node : flowPane.getChildren()) {
	        if (node instanceof VBox){
	        	for (Node node1 : ((VBox) node).getChildren()){
	        		if (node1 instanceof ImageView) {
	        			setPlayerToken(0, -1);
	        	        int next = nextToken();
	        			((ImageView) node1).setImage(getImage(next));
	                    setPlayerToken(0, next);
	                    MusicController.playSwitch();
	                    return;
	        		}
	        	}
	        }
        }
    }

    @FXML
    private void imageClicked(ImageView imageView){
    	MusicController.clear();
        int index = 0;
        for (Node node : flowPane.getChildren()){
            if (node instanceof VBox){
                for (Node node1 : ((VBox) node).getChildren()){
                    if (node1 instanceof ImageView) {
                        ImageView image = (ImageView) node1;
                        if (image == imageView) {
                        	setPlayerToken(index, -1);
                            int next = nextToken();
                            ((ImageView) node1).setImage(getImage(next));
                            setPlayerToken(index, next);
                            MusicController.playSwitch();
                            return;
                        }
                    }
                }
            }
            index++;
        }
    }

    @FXML
    public void backButtonClicked() throws IOException {
    	MusicController.playBack();
        PlayerNumSelectionScreen.start();
        server.kill();
    }

    @FXML
    public void createGameButtonClicked() throws IOException, JSONException{

    	MusicController.playNext();  
        new GameEngine(boardNum);

        GameEngine.setServer(server);

        int tokenIndex = 0;
        for (Node node : flowPane.getChildren()){
            if (node instanceof VBox){
                for (Node node1 : ((VBox) node).getChildren()){
                    if (node1 instanceof TextField) {
                        String playerName = ((TextField) node1).getText();
                        GameEngine.addPlayer(new Player(playerName, token.get(tokenIndex), 0, 0));
                        tokenIndex++;
                    }
                }
            }
        }
        
        new GameScreen();
        GameScreen.start();
    }

    public void playerChangeToken(int player) {
        MusicController.clear();
        VBox vbox = (VBox) flowPane.getChildren().get(player - 1);
        ImageView img = (ImageView) vbox.getChildren().get(0);
        MusicController.playSwitch();
        int nextToken = nextToken();
        img.setImage(getImage(nextToken));
        setPlayerToken(player - 1, nextToken);
    }

    public void playerChangeName(int player, String playerName){
        VBox vbox = (VBox) flowPane.getChildren().get(player - 1);
        TextField nameField = (TextField) vbox.getChildren().get(1);
        nameField.setText(playerName);
    }

    public void setStage(Stage stage) {
        stage.setOnCloseRequest(windowEvent -> {
            try {
                server.kill();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
