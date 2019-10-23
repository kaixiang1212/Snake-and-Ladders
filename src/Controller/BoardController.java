package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import View.GameScreen;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BoardController {
	
	@FXML
	private Board gameboard;
	private GridPane squares;
	private List<ImageView> initialEntities;
	//private Player player;
	private Stage stage;
	private GameScreen gamescreen;
	    

	public BoardController(Board gameboard, List<ImageView> initialEntities, Stage s, GameScreen game) {
		// The board contains the current game details at this point. Game is up to date.
		//this.player = gameboard.getPlayer();
		this.gameboard = gameboard;
		this.initialEntities = new ArrayList<>(initialEntities);
		this.stage = s;
		this.gamescreen = game;
	}

	@FXML
	public void initialize() {
        Image boardFloor = new Image("/GameTile.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < gameboard.getWidth(); x++) {
            for (int y = 0; y < gameboard.getHeight(); y++) {
                squares.add(new ImageView(boardFloor), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

    }
	
	@FXML
    public void handleKeyPress(KeyEvent event) {
        /**
         * switch (event.getCode()) {

        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        default:
            break;
        }
                 */
	}
}