package View;

import Model.*;
import Controller.*;

import java.util.List;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class BoardEntityLoader extends BoardLoader{

	private static List<Pair<Entity,ImageView>> entities;
	private static Stage stage;
	    
	public BoardEntityLoader(String filename, Stage s) throws FileNotFoundException, JSONException {
		super(filename);
		entities = new ArrayList<>();
		stage = s;
	}      
	
	public static void addEntity(Entity entity, ImageView view) {
		trackPosition(entity, view);
		entities.add(new Pair<>(entity, view));
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
	private static void trackPosition(Entity entity, ImageView node) {
		if (node.getId().equals("snake")) {
		    GridPane.setColumnIndex(node, entity.getX());
			GridPane.setRowIndex(node, GameEngine.getBoard().getHeight()-1-entity.getY());
			GridPane.setColumnSpan(node, 4);
			GridPane.setRowSpan(node, 2);
			
		} else if (node.getId().equals("coralsnake")) {
		    GridPane.setColumnIndex(node, entity.getX());
			GridPane.setRowIndex(node, GameEngine.getBoard().getHeight()-1-entity.getY());
			GridPane.setColumnSpan(node, 6);
			GridPane.setRowSpan(node, 2);
			
		} else {
			GridPane.setColumnIndex(node, entity.getX());
			GridPane.setRowIndex(node, GameEngine.getBoard().getHeight()-1-entity.getY());
			node.setPreserveRatio(true);
		}
			
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
	        	GridPane.setRowIndex(node, GameEngine.getBoard().getHeight()-1-newValue.intValue());
	        	if(entity instanceof Player) {
	        		Player playerEntity = (Player) entity;
	        		switch(playerEntity.getPlayerToken()) {
	        			default:
	        				if(newValue.intValue()%2 == 1) {
		        				node.setScaleX(-1);
		        			} else {
		        				node.setScaleX(1);
		        			}
	        				break;
	        		}
	        	}
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
		
	    /*        
		entity.getImageString().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
	        	System.out.println(entity.getEntityName() + " change to " + newValue);
	        }
		});
 		*/
	}
	
	public static void configBoardController(BoardController boardController) throws JSONException {
		load();
		boardController.config(entities, stage);
	}      

}
