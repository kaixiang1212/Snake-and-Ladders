package View;

import Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/** 
 * 
 * Started code taken from:
 * Edited by: 
 *
 * This class loads a Game Board from a JSON file
 * 
 */


public abstract class BoardLoader {
	
	private static JSONObject json;

    public BoardLoader(String filename) throws FileNotFoundException, JSONException {
    	String path = getClass().getClassLoader().getResource("boards/" + filename).getPath().replaceAll("%20", " ");
    	assert(!path.isEmpty());
        json = new JSONObject(new JSONTokener(new FileReader(path)));
    }

    /**
     * Parses the JSON to create a Board.
     * @return
     * @throws JSONException 
     */
	public static void load () throws JSONException {
		int width = json.getInt("width");
		int height = json.getInt("height");
	        
		Board gameboard = new Board(width, height);
		GameEngine.setBoard(gameboard);
		
		JSONArray jsonEntities = json.getJSONArray("entities");
		
		for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(jsonEntities.getJSONObject(i));
		}
		
		for(int i = GameEngine.getPlayers().size()-1; i >= 0; i--) {
			Player player = GameEngine.getPlayers().get(i);
			onLoad(player);
		}
		
		JSONArray itemPool = json.getJSONArray("items");
		for(int i = 0; i < itemPool.length(); i++) {
			LoadItem(itemPool.getJSONObject(i), gameboard);
		}
	}


    protected static void LoadItem(JSONObject jsonItem, Board gameboard) throws JSONException {
    	String type = jsonItem.getString("type");
    	String name = jsonItem.getString("name");
    	String description = jsonItem.getString("description");
    	int frequency = jsonItem.getInt("frequency");
    	int expiry = jsonItem.getInt("expiry");
    	Item item = new Item(-1, -1, Item.ItemType.valueOf(type), name, description, frequency, expiry);
    	gameboard.includeItem(item);
    }

	private static void loadEntity(JSONObject json) throws JSONException {
        String type = json.getString("type");
        String id = json.getString("id");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int x2 = json.getInt("x2");
        int y2 = json.getInt("y2");
        switch (type) {
        case "player":
        	int nextTokenNum = GameEngine.getPlayerNum();
            Player player = new Player("Player " + nextTokenNum+1, nextTokenNum, x, y);
            onLoad(player);
            if (player != null) {
            	GameEngine.addPlayer(player);
            }
            break;
        // All Snake Entities:
        case "snake":
            Snake snake = new Snake(x, y, x2, y2, type);
            onLoad(snake);
            if (snake != null) {
            	GameEngine.getBoard().addEntity(snake);
            }
            break;
        case "bluesnake":
            Snake bsnake = new Snake(x, y, x2, y2, type);
            onLoad(bsnake);
            if (bsnake != null) {
            	GameEngine.getBoard().addEntity(bsnake);
            }
            break;
        case "pinksnake":
            Snake psnake = new Snake(x, y, x2, y2, type);
            onLoad(psnake);
            if (psnake != null) {
            	GameEngine.getBoard().addEntity(psnake);
            }
            break;
        case "ladder":
        	Ladder ladder = new Ladder(x, y, x2, y2, id);
        	onLoad(ladder);
            if (ladder != null) {
            	GameEngine.getBoard().addEntity(ladder);
            }
        	break;
        }
    }

	public static void onLoad(Player player) {
		ImageView view = new ImageView(new Image(String.valueOf(BoardEntityLoader.class.getClassLoader().getResource("asset/token" + player.getPlayerToken() + ".png"))));
		view.setPreserveRatio(true);
		view.setFitHeight(GameScreen.getSceneHeight()/(float)GameEngine.getBoard().getHeight()*0.75f);
		view.setId("player");
		BoardEntityLoader.addEntity(player, view);
	}
	
	// No longer using ImageViews internally through code: all fed directly into fxml
	public static void onLoad(Snake snake) {
		/**if (snake.getSnaketype() == SnakeType.SNAKE) {
			ImageView view = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/pipe_top.png"))));
			view.setPreserveRatio(true);
			view.setFitHeight(gamescreen.getSceneHeight()/(float)engine.getBoard().getHeight()*1.0f);
			view.setFitHeight(800);
			view.setFitWidth(300);
			view.setId("snake");
			addEntity(snake, view);
		} else if (snake.getSnaketype() == SnakeType.BLUESNAKE) {
			Image snakeimg = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/1.png")), 2600, 2141, true, true);
			ImageView view = new ImageView(snakeimg);
			view.setFitHeight(210);
			view.setFitWidth(260);
			//view.setPreserveRatio(true);
			view.setId("snake");
			addEntity(snake, view);
		} else if (snake.getSnaketype() == SnakeType.PINKSNAKE) {
			/**Image snakeimg = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/coralsnake.png")), 463, 572, true, false);
			snakeimg.getRequestedHeight();
			ImageView view = new ImageView(snakeimg);
			*/
		/**
			Image snakeimg = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/coralsnake.png")), 463, 572, true, true);
			ImageView view = new ImageView();
			view.setFitWidth(332);
			view.setFitHeight(386);
			//view.setPreserveRatio(true);
			view.setImage(snakeimg);
			view.setId("coralsnake");
			addEntity(snake, view);
		}
		
		*/
	}

	public static void onLoad(Ladder ladder) {
		/**ImageView view = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/vine_start.png"))));
		view.setPreserveRatio(true);
		view.setFitHeight(gamescreen.getSceneHeight()/(float)engine.getBoard().getHeight()*1.0f);
		view.setId("ladder");
		addEntity(ladder, view);
		*/
	}
	
	public static void onLoad(Item item) {
		ImageView view = new ImageView(new Image(String.valueOf(BoardEntityLoader.class.getClassLoader().getResource("asset/items/item" + item.getItemType().ordinal() + ".png"))));
		view.setPreserveRatio(true);
		view.setFitHeight(GameScreen.getSceneHeight()/(float)GameEngine.getBoard().getHeight()*0.75f);
		view.setId("item" + item.getItemType().ordinal());
		BoardEntityLoader.addEntity(item, view);
	}

  
}
