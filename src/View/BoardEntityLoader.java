package View;

import Model.*;
import Controller.*;

import java.util.List;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class BoardEntityLoader extends BoardLoader{

	private List<Pair<Entity,ImageView>> entities;

	//Images
	private Stage stage;
 	private GameScreen gamescreen;
	private GameEngine engine;
	    
	public BoardEntityLoader(String filename, Stage s, GameScreen game, GameEngine engine) throws FileNotFoundException, JSONException {
		super(filename);
		
		entities = new ArrayList<>();
		//snakeBlueImage = new Image("/snake1.png");
		//ladderImage = new Image("");
		
		// All player images added here
		stage = s;
		gamescreen = game;
		this.engine = engine;
	}

	      
	
	private void addEntity(Entity entity, ImageView view) {
		trackPosition(entity, view);
		entities.add(new Pair<>(entity, view));
	}

	@Override
	public void onLoad(Player player) {
		ImageView view = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/token" + player.getPlayerToken() + ".png"))));
		view.setPreserveRatio(true);
		view.setFitHeight(gamescreen.getSceneHeight()/(float)engine.getBoard().getHeight()*0.7f);
		view.setId("player");
		addEntity(player, view);
//		Player p = (Player) player;
//		p.giveStage(this.stage);
	}

	@Override
	public void onLoad(Snake snake) {
		ImageView view = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/pipe_top.png"))));
		view.setPreserveRatio(true);
		view.setFitHeight(gamescreen.getSceneHeight()/(float)engine.getBoard().getHeight()*1.0f);
		view.setId("snake");
		addEntity(snake, view);
	}
	
	@Override
	public void onLoad(Ladder ladder) {
		ImageView view = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/vine_start.png"))));
		view.setPreserveRatio(true);
		view.setFitHeight(gamescreen.getSceneHeight()/(float)engine.getBoard().getHeight()*1.0f);
		view.setId("ladder");
		addEntity(ladder, view);
	}
	


	/**
	* Set a node in a GridPane to have its position track the position of an
	* entity in the dungeon.
	*
	* By connecting the model with the view in this way, the model requires no
	* knowledge of the view and changes to the position of entities in the
	* model will automatically be reflected in the view.
	* @param entity
	* @param node
	*/
	private void trackPosition(Entity entity, ImageView node) {
		GridPane.setColumnIndex(node, entity.getX());
		GridPane.setRowIndex(node, engine.getBoard().getHeight()-1-entity.getY());
		entity.x().addListener(new ChangeListener<Number>() {
			@Override
	    	public void changed(ObservableValue<? extends Number> observable,
	        	Number oldValue, Number newValue) {
	        	GridPane.setColumnIndex(node, newValue.intValue());
	    	}
		});
	            
	            
	            
		entity.y().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
	        	Number oldValue, Number newValue) {
	        	GridPane.setRowIndex(node, engine.getBoard().getHeight()-1-newValue.intValue());
			}
		});
		
		
		entity.getIsVisible().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
				Boolean oldValue, Boolean newValue) {
				node.setVisible(false);
				entities.remove(node);
			}
		});
		
	            
		entity.getImage().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
	        	System.out.println(entity.getEntityName() + " change to " + newValue);
	        }
		});
	}
	
	
	// private void trackPosition(Entity entity, Image) {

	public void configBoardController(BoardController boardController) throws JSONException {
		boardController.config(load(engine), this.entities, this.stage, this.gamescreen);
	}



	@Override
	public void changeImage(Entity entity, String string) {
		// TODO Auto-generated method stub
		
	}
	    
	    

}
