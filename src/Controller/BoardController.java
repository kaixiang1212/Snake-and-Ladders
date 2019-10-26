package Controller;

import Model.*;
import View.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BoardController {
	
	@FXML
	private GridPane squares;
	
	private List<ImageView> initialEntities;
	//private Player player;
	GameEngine engine;
	private Stage stage;
	private GameScreen gamescreen;
	    

	public BoardController(GameEngine engine, List<ImageView> initialEntities, Stage s, GameScreen game) {
		// The board contains the current game details at this point. Game is up to date.
		//this.player = gameboard.getPlayer();
		this.engine = engine;
		this.initialEntities = new ArrayList<>(initialEntities);
		this.stage = s;
		this.gamescreen = game;
	}

	@FXML
	public void initialize() {
        Image boardFloor = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/gametile.png")));

        for (int x = 0; x < engine.getBoard().getWidth(); x++) {
            for (int y = engine.getBoard().getHeight()-1; y >=0 ; y--) {
                ImageView floorView = new ImageView(boardFloor);
                floorView.setFitHeight(gamescreen.getHeight()/(float)engine.getBoard().getHeight());
                floorView.setFitWidth(gamescreen.getHeight()/(float)engine.getBoard().getWidth());
                squares.add(floorView, x, y);
            }
        }
       
        
        for (ImageView entity : initialEntities) {
            squares.getChildren().add(entity);
            
        }
        
        for(Node child : squares.getChildren()) {
        	GridPane.setHalignment(child, HPos.CENTER);
        }

    }
	
	@FXML
    public void handleKeyPress(KeyEvent event) {
        
         switch (event.getCode()) {

        case UP:
            engine.rollDice();
            engine.nextPlayer();
            break;
        case DOWN:
            break;
        case LEFT:
            break;
        case RIGHT:
            break;
        default:
            break;
        }
                 
	}
}