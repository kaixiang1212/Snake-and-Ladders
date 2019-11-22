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
	public ImageView Ladder1;
	@FXML
	public ImageView gifLadder1;
	@FXML
	public ImageView Ladder2;
	@FXML
	public ImageView gifLadder2;
	@FXML
	public ImageView Ladder3;
	@FXML
	public ImageView gifLadder3;
	@FXML
	public ImageView Ladder4;
	@FXML
	public ImageView gifLadder4;
	@FXML
	public ImageView Ladder5;
	@FXML
	public ImageView gifLadder5;
	@FXML
	public ImageView Ladder6;
	@FXML
	public ImageView gifLadder6;
	@FXML
	public ImageView Ladder7;
	@FXML
	public ImageView gifLadder7;
	@FXML
	private AnchorPane menuPane;
	@FXML
	private Button exitButton;
	@FXML
	private Button resumeButton;
	
	private List<Pair<Entity, ImageView>> initialEntities;
    private GameEngine engine;
	private Stage stage;
	private GameScreen gamescreen;
	private MusicController musicController;
	
	public BoardController() {
		musicController = new MusicController();
		musicController.initBoard();
	}

    /**
     * Configuration for Board Controller and configure Dice Controller
     * @param engine Game Engine
     * @param initialEntities
     * @param s Stage
     * @param game Game Screen
     */
	public void config(GameEngine engine, List<Pair<Entity, ImageView>> initialEntities, Stage s, GameScreen game) {
		this.engine = engine;
		this.initialEntities = new ArrayList<>(initialEntities);
		stage = s;
		this.gamescreen = game;
		diceController.config(engine, this);
		hideMenu();
		//ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> onWindowResize();
		//stage.widthProperty().addListener(stageSizeListener);
		//stage.heightProperty().addListener(stageSizeListener);
		musicController.playBGM();
		
	}
	
	/**
	 * Called when the board screen is loaded
	 */
	@FXML
	public void init() {
//		int lastrand = 0, rand = 0;
//		int[] lastrandv = new int[engine.getBoard().getWidth()];
		
		// Adds gametiles to the gridpane
		for (int y = 0; y < engine.getBoard().getHeight(); y++) {
			for (int x = 0; x < engine.getBoard().getWidth(); x++) {
//				while (rand == lastrand || rand == lastrandv[x])
//					rand = (int) (Math.random() * 6);
				int tileid = (x%2 + y%2)%2;
				if(engine.getBoard().isSnake(x, engine.getBoard().getHeight() - y - 1) != null) {
					tileid = 7;
				} else if(engine.getBoard().isLadder(x, engine.getBoard().getHeight() - y - 1) != null) {
					tileid = 4;
				}
				Image boardFloor = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/Gametile" + tileid + ".jpg")));
				ImageView floorView = new ImageView(boardFloor);
				floorView.setPreserveRatio(true);
				floorView.setFitHeight(gamescreen.getSceneHeight() / (float) engine.getBoard().getHeight());
				floorView.setId("tile");
				squares.add(floorView, x, y);				
				Text tilenum = new Text(Integer.toString(engine.getBoard().getPosition(x, engine.getBoard().getHeight() - y - 1)));
				tilenum.setFont(Font.font("Papyrus", 42));
				tilenum.setFill(Color.BLACK);
				//tilenum.setStroke(Color.BLACK);
				//tilenum.setStrokeWidth(2);
				squares.add(new StackPane(tilenum), x, y);
				GridPane.setHalignment(tilenum, HPos.CENTER);
//				lastrand = rand;
//				lastrandv[x] = rand;
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
        musicController.clear();
        musicController.stopBGM();
        StartGameScreen startGameScreen = new StartGameScreen(stage);
        startGameScreen.start();
        engine.killServer();
    }
    
    /**
     * Called when the resume button is clicked from the pause menu
     * @throws IOException
     */
    @FXML
    private void handleResumeButton() throws IOException {
    	diceController.menuButtonClicked();
    }
    
	/**
	 * Shows the pause menu (doesn't pause/unpause the game by itself)
	 */
    public void showMenu() {
    	menuPane.setManaged(true);
        menuPane.setVisible(true);
    }
    
    /**
	 * Hides the pause menu (doesn't pause/unpause the game by itself)
	 */
    public void hideMenu() {
    	menuPane.setManaged(false);
        menuPane.setVisible(false);
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
    
	/**
	 * Called when the board window is resized (UNUSED)
	 */
    /*
	public void onWindowResize() {
		for(Node node : squares.getChildren()) {
			if(node instanceof ImageView) {
				ImageView image = (ImageView) node;
				if(image.getId() != null && image.getId().equals("player")) {
					image.setFitHeight(gamescreen.getSceneHeight() / (float) engine.getBoard().getHeight()*0.75f);
				} else {
					image.setFitHeight(gamescreen.getSceneHeight() / (float) engine.getBoard().getHeight());
				}		
			} else {
				double scale = gamescreen.getSceneHeight() / (double) gamescreen.getHeight();
				node.setScaleX(scale);
				node.setScaleY(scale);
			}
		}
	}
    */
}