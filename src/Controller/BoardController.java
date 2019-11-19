package Controller;

import Model.*;
import View.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Pair;

public class BoardController {
	
	@FXML
	private HBox hbox;
	@FXML
	private GridPane squares;
	@FXML
	private VBox dice;
	@FXML
	private DiceController diceController;
	@FXML
	private ImageView Ladder1;
	@FXML
	private ImageView gifLadder1;
	@FXML
	private ImageView Ladder2;
	@FXML
	private ImageView gifLadder2;
	@FXML
	private ImageView Ladder3;
	@FXML
	private ImageView gifLadder3;
	@FXML
	private ImageView Ladder4;
	@FXML
	private ImageView gifLadder4;
	@FXML
	private ImageView Ladder5;
	@FXML
	private ImageView gifLadder5;
	@FXML
	private ImageView Ladder6;
	@FXML
	private ImageView gifLadder6;
	@FXML
	private ImageView Ladder7;
	@FXML
	private ImageView gifLadder7;
	@FXML
	private AnchorPane menuPane;
	@FXML
	private Button exitButton;
	@FXML
	private Button resumeButton;
	@FXML
	private Button musicButton;
	@FXML
	private Button soundFXButton;
	
	private List<Pair<Entity, ImageView>> initialEntities;
	
	public BoardController() {
	}

    /**
     * Configuration for Board Controller and configure Dice Controller
     * @param engine Game Engine
     * @param initialEntities
     */
	public void config(List<Pair<Entity, ImageView>> initialEntities) {
		this.initialEntities = new ArrayList<>(initialEntities);
		diceController.config(this);
		hideMenu();
	}
	
	/**
	 * Called when the board screen is loaded
	 */
	@FXML
	public void init() {
		// Adds gametiles to the gridpane
		for (int y = 0; y < GameEngine.getBoard().getHeight(); y++) {
			for (int x = 0; x < GameEngine.getBoard().getWidth(); x++) {
				int tileid = (x%2 + y%2)%2;
				if(GameEngine.getBoard().isSnake(x, GameEngine.getBoard().getHeight() - y - 1) != null) {
					tileid = 7;
				} else if(GameEngine.getBoard().isLadder(x, GameEngine.getBoard().getHeight() - y - 1) != null) {
					tileid = 4;
				}
				Image boardFloor = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/Gametile" + tileid + ".jpg")));
				ImageView floorView = new ImageView(boardFloor);
				floorView.setPreserveRatio(true);
				floorView.setFitHeight(GameScreen.getSceneHeight() / (float) GameEngine.getBoard().getHeight());
				floorView.setId("tile");
				squares.add(floorView, x, y);				
				Text tilenum = new Text(Integer.toString(GameEngine.getBoard().getPosition(x, GameEngine.getBoard().getHeight() - y - 1)));
				tilenum.setFont(Font.font("Papyrus", 42));
				tilenum.setFill(Color.BLACK);
				squares.add(new StackPane(tilenum), x, y);
				GridPane.setHalignment(tilenum, HPos.CENTER);
			}
		}
		
		// Adds initial entities (players, items) to the gridpane
		for (Pair<Entity, ImageView> entityPair : initialEntities) {
			ImageView entityImage = entityPair.getValue();
			squares.getChildren().add(entityImage);
			GridPane.setHalignment(entityImage, HPos.CENTER);
		}
		
		// Play BGM
		MusicController.initBoard();
		MusicController.playBGM();
	}
	
	public ImageView getGif(String id) {
		// currently only returns the gifLadder1. once tested, make getter for all imageviews based on string id.
		ImageView view = null;
		if (id.equals("gifLadder1")) {
			view = gifLadder1;
		} else if(id.equals("gifLadder2")) {
			view = gifLadder2;
		} else if(id.equals("gifLadder3")) {
			view = gifLadder3;
		} else if(id.equals("gifLadder4")) {
			view = gifLadder4;
		} else if(id.equals("gifLadder5")) {
			view = gifLadder5;
		} else if(id.equals("gifLadder6")) {
			view = gifLadder6;
		} else if(id.equals("gifLadder7")) {
			view = gifLadder7;
		}
		return view;
	}
	
	public ImageView getImg(String id) {
		// currently only returns the gifLadder1. once tested, make getter for all imageviews based on string id.
		ImageView img = null;
		if (id.equals("Ladder1")) {
			img = Ladder1;
		} else if(id.equals("Ladder2")) {
			img = Ladder2;
		} else if(id.equals("Ladder3")) {
			img = Ladder3;
		} else if(id.equals("Ladder4")) {
			img = Ladder4;
		} else if(id.equals("Ladder5")) {
			img = Ladder5;
		} else if(id.equals("Ladder6")) {
			img = Ladder6;
		} else if(id.equals("Ladder7")) {
			img = Ladder7;
		}
		return img;
	}
	
	
	public void shakeLadder(ImageView ladderGif, ImageView ladderImg) {
		ladderImg.setVisible(false);
		ladderGif.setVisible(true);
	}
	

	public void stopShakeLadder(ImageView ladderGif, ImageView ladderImg) {
		ladderGif.setVisible(false);
		ladderImg.setVisible(true);
	}
	/**
	 * Called when the exit button is clicked from the pause menu
	 * @throws IOException
	 */
    @FXML
    private void handleExitButton() throws IOException {
    	hideMenu();
    	MusicController.clear();
    	MusicController.stopBGM();
    	MusicController.playBack();
        StartGameScreen.start();
    }
    
    /**
     * Called when the resume button is clicked from the pause menu
     * @throws IOException
     */
    @FXML
    private void handleResumeButton() throws IOException {
    	diceController.menuButtonClicked();
    }
    
    @FXML
    private void handleMusicButton() throws IOException {
    	MusicController.toggleMusic();
    	MusicController.playSwitch();
    	if(MusicController.getMusicToggle()) {
    		musicButton.setText("Music: ON");
    	} else {
    		musicButton.setText("Music: OFF");
    	}
    }
    
    @FXML
    private void handleSoundFXButton() throws IOException {
    	MusicController.togglefx();
    	MusicController.playSwitch();
    	if(MusicController.getFxToggle() == true) {
    		soundFXButton.setText("Sound FX: ON");
    	} else {
    		soundFXButton.setText("Sound FX: OFF");
    	}
    }
    
	/**
	 * Shows the pause menu (doesn't pause/unpause the game by itself)
	 */
    public void showMenu() {
    	menuPane.setManaged(true);
        menuPane.setVisible(true);
        MusicController.pauseBGM();
        if(MusicController.getFxToggle() == true) {
    		soundFXButton.setText("Sound FX: ON");
    	} else {
    		soundFXButton.setText("Sound FX: OFF");
    	}
        if(MusicController.getMusicToggle()) {
    		musicButton.setText("Music: ON");
    	} else {
    		musicButton.setText("Music: OFF");
    	}
    }
    
    /**
	 * Hides the pause menu (doesn't pause/unpause the game by itself)
	 */
    public void hideMenu() {
    	menuPane.setManaged(false);
        menuPane.setVisible(false);
        if(MusicController.getMusicToggle()) {
    		MusicController.playBGM();
    	}
    }
    
    /**
     * Spawns a random item on the board
     */
    public void spawnItem() {
    	Item item = GameEngine.spawnRandomItem();
    	if(item != null) {
    		System.out.println("[!] Spawning item at position " + GameEngine.getBoard().getPosition(item.getX(), item.getY()) + ": " + item.getName());
    		System.out.println("\t- " + item.getDescription() + "\n");
    		ImageView view = item.getImage();
    		view.setPreserveRatio(true);
    		view.setFitHeight(GameScreen.getHeight()/(float)GameEngine.getBoard().getHeight()*0.65f);
    		squares.getChildren().add(squares.getChildren().size()-GameEngine.getPlayerNum(), view);
    		GridPane.setColumnIndex(view, item.getX());
    		GridPane.setRowIndex(view, GameEngine.getBoard().getHeight() - 1 - item.getY());
			GridPane.setHalignment(view, HPos.CENTER);
			MusicController.playItemAppear();
    	} else {
    		System.out.println("[!] Item spawn failed: space occupied." + "\n");
    	}
    }
    
    /**
     * Clears any expired items from the board (expiryCounter == 0)
     */
    public void cleanExpiredItems() {
        ArrayList<Item> expired = new ArrayList<Item>();   
    	for(Item item : GameEngine.getBoard().getSpawnedItems()) {
        	ImageView itemView = item.getImage();
    		if(item.getExpiry() == 0) {
    			expired.add(item);
    			for(Node node : squares.getChildren()) {
    				if(node instanceof ImageView) {
    					ImageView nodeView = (ImageView) node;
    					if(nodeView.equals(itemView)) {
    						squares.getChildren().remove(nodeView);
    						break;
    					}
    				}
    			}
    			MusicController.playItemDisappear();
    			System.out.println("[!] " + item.getName() + " item expired." + "\n");
    		} else {
    			item.decrementExpiry();
    		}
        }
        GameEngine.getBoard().removeItems(expired);
    }
    
    /**
     * Clears picked up items from the board as soon as they are picked up (expiryCounter == GameEngine.pickedUpItemExpiry == -1000)
     */
    public void cleanPickedUpItems() {
    	ArrayList<Item> pickedup = new ArrayList<Item>();   
    	for(Item item : GameEngine.getBoard().getSpawnedItems()) {
        	ImageView itemView = item.getImage();
    		if(item.getExpiry() == GameEngine.getPickedUpItemExpiry()) {
    			pickedup.add(item);
    			for(Node node : squares.getChildren()) {
    				if(node instanceof ImageView) {
    					ImageView nodeView = (ImageView) node;
    					if(nodeView.equals(itemView)) {
    						squares.getChildren().remove(nodeView);
    						break;
    					}
    				}
    			}
    			MusicController.playItemPickup();
    		}
        }
        GameEngine.getBoard().removeItems(pickedup);
    }

}