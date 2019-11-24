package View;

import Model.*;
import Model.Board.BoardType;
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
	
	private static JSONObject boardJson;
	private static JSONObject itemsJson;

    public BoardLoader(String filename) throws FileNotFoundException, JSONException {
    	String path = getClass().getClassLoader().getResource("boards/" + filename).getPath().replaceAll("%20", " ");
    	assert(!path.isEmpty());
        boardJson = new JSONObject(new JSONTokener(new FileReader(path)));
        path = getClass().getClassLoader().getResource("boards/items.json").getPath().replaceAll("%20", " ");
        assert(!path.isEmpty());
        itemsJson = new JSONObject(new JSONTokener(new FileReader(path)));
    }

    /**
     * Parses the JSON to create a Board.
     * @return
     * @throws JSONException 
     */
	public static void load(BoardType type) throws JSONException {
		int width = boardJson.getInt("width");
		int height = boardJson.getInt("height");
	    
		Board gameboard = new Board(width, height, type);
		new GameEngine(gameboard);
		
		JSONArray jsonEntities = boardJson.getJSONArray("entities");
		
		for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(jsonEntities.getJSONObject(i));
		}
		
		for(int i = GameEngine.getPlayers().size()-1; i >= 0; i--) {
			Player player = GameEngine.getPlayers().get(i);
			onLoad(player);
		}
		
		JSONArray itemPool = itemsJson.getJSONArray("items");
		for(int i = 0; i < itemPool.length(); i++) {
			LoadItem(itemPool.getJSONObject(i), gameboard);
		}
	}

    protected static void LoadItem(JSONObject jsonItem, Board gameboard) throws JSONException {
    	String type = jsonItem.getString("type");
    	String name = jsonItem.getString("name");
    	String description = jsonItem.getString("description");
    	Item.setDescription(Item.ItemType.valueOf(type).ordinal(), description);
    	int frequency = jsonItem.getInt("frequency");
    	int expiry = jsonItem.getInt("expiry");
    	Item item = new Item(-1, -1, Item.ItemType.valueOf(type), name, frequency, expiry);
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
        case "snake":
            Snake snake = new Snake(x, y, x2, y2, id);
            onLoad(snake);
            if (snake != null) {
            	GameEngine.getBoard().addEntity(snake);
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
		ImageView view = player.getImage();
		view.setPreserveRatio(true);
		view.setFitHeight(GameScreen.getSceneHeight()/(float)GameEngine.getBoard().getHeight()*0.75f);
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
		ImageView view = item.getImage();
		view.setPreserveRatio(true);
		view.setFitHeight(GameScreen.getSceneHeight()/(float)GameEngine.getBoard().getHeight()*0.65f);
		BoardEntityLoader.addEntity(item, view);
	}

  
}
