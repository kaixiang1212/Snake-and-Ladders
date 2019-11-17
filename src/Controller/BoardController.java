package Controller;

import Model.*;
import View.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
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
	private Stage stage;
	
	public BoardController() {
		MusicController.initBoard();
	}

    /**
     * Configuration for Board Controller and configure Dice Controller
     * @param engine Game Engine
     * @param initialEntities
     * @param s Stage
     */
	public void config(List<Pair<Entity, ImageView>> initialEntities, Stage s) {
		this.initialEntities = new ArrayList<>(initialEntities);
		stage = s;
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
        new StartGameScreen(stage);
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
    	if(MusicController.getMusicToggle()) {
    		musicButton.setText("Music: ON");
    	} else {
    		musicButton.setText("Music: OFF");
    	}
    }
    
    @FXML
    private void handleSoundFXButton() throws IOException {
    	MusicController.togglefx();
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
    }
    
    public GridPane getGridPane() {
    	return squares;
    }
    
	/**
	 * Used to render pipe and vine segments onto the gridpane (UNUSED)
	 */
	/*
	public void addSegments(Entity entity) {
		int x, y, x_end, y_end, y_init, x_init;
		String name;
		if (entity instanceof Snake) {
			x = entity.getX();
			y = entity.getY();
			x_end = ((Snake) entity).getTail().getKey();
			y_end = ((Snake) entity).getTail().getValue();
			name = "pipe";
			y_init = y;
			y--;
		} else if (entity instanceof Ladder) {
			entity = (Ladder) entity;
			x_end = entity.getX();
			y_end = entity.getY();
			x = ((Ladder) entity).getTop().getKey();
			y = ((Ladder) entity).getTop().getValue();
			name = "vine";
			y_init = y;

		} else {
			return;
		}
		x_init = x;
		ImageView image = null;

		while (x != x_end || y != y_end) {
			if (x == x_init) {
				if (x > x_end) {
					image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_c_lefttop.png"))));
				} else if (x < x_end) {
					image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_c_righttop.png"))));
				} else {
					image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_v.png"))));
				}
			} else {
				if (x > x_end) {
					image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_l.png"))));
				} else if (x < x_end) {
					image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_r.png"))));
				} else {
					if ((y == y_init && entity instanceof Ladder) || (y == y_init - 1 && entity instanceof Snake)) {
						if (x < x_init) {
							image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_c_rightbottom.png"))));
						} else {
							image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_c_leftbottom.png"))));
						}
					} else {
						image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_v.png"))));
					}
				}
			}
			image.setFitHeight(gamescreen.getHeight() / (float) engine.getBoard().getHeight() * 1.0f);
			image.setPreserveRatio(true);
			squares.add(image, x, engine.getBoard().getHeight() - 1 - y);
			if (x > x_end) {
				x--;
			} else if (x < x_end) {
				x++;
			} else {
				y--;
			}

		}
		if (entity instanceof Snake) {
			image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/" + name + "_end.png"))));
			image.setFitHeight(gamescreen.getHeight() / (float) engine.getBoard().getHeight() * 1.0f);
			image.setPreserveRatio(true);
			squares.add(image, x, engine.getBoard().getHeight() - 1 - y);
		}

	}
	*/

}