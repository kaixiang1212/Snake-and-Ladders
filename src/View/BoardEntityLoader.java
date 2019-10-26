package View;

import Model.*;
import Controller.*;

import java.util.List;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class BoardEntityLoader extends BoardLoader{

	private List<ImageView> entities;

	//Images
	private Image playerWhiteImage;
	private Stage stage;
 	private GameScreen gamescreen;
	    
	    
	public BoardEntityLoader(String filename, Stage s, GameScreen game) throws FileNotFoundException, JSONException {
		super(filename);
		
		entities = new ArrayList<>();
		//snakeBlueImage = new Image("/snake1.png");
		//ladderImage = new Image("");
		
		// All player images added here
		playerWhiteImage = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/playerpiece.png"))); 
		stage = s;
		gamescreen = game;
	}

	      
	
	private void addEntity(Entity entity, GameEngine engine, ImageView view) {
		trackPosition(entity, engine, view);
		entities.add(view);
	}

	@Override
	public void onLoad(Player player, GameEngine engine) {
		ImageView view = new ImageView(playerWhiteImage);
		view.setFitHeight(gamescreen.getHeight()/(float)engine.getBoard().getHeight()*0.7f);
		view.setPreserveRatio(true);
		addEntity(player, engine, view);
//		Player p = (Player) player;
//		p.giveStage(this.stage);
	}

	@Override
	public void onLoad(Snake snake, GameEngine engine) {
		ImageView view = new ImageView(playerWhiteImage);
		view.setFitHeight(gamescreen.getHeight()/(float)engine.getBoard().getHeight()*0.7f);
		view.setPreserveRatio(true);
		addEntity(snake, engine, view);
	}
	
	@Override
	public void onLoad(Ladder ladder, GameEngine engine) {
		ImageView view = new ImageView(playerWhiteImage);
		view.setFitHeight(gamescreen.getHeight()/(float)engine.getBoard().getHeight()*0.7f);
		view.setPreserveRatio(true);
		addEntity(ladder, engine, view);
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
	private void trackPosition(Entity entity, GameEngine engine, ImageView node) {
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
	        	GridPane.setRowIndex(node, newValue.intValue());
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

	/**
	* Create a controller that can be attached to the DungeonView with all the
	* loaded entities.
	* @return
	* @throws FileNotFoundException
	 * @throws JSONException 
	*/
	public BoardController loadController() throws FileNotFoundException, JSONException {
		return new BoardController(load(), this.entities, this.stage, this.gamescreen);
	}



	@Override
	public void changeImage(Entity entity, String string) {
		// TODO Auto-generated method stub
		
	}
	    
	    

}
