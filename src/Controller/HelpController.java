package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Model.Item;
import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.text.*;

public class HelpController {
    
	@FXML
    private Button backButton;
	@FXML
	private Button nextButton;
	@FXML
	private Label title;
	@FXML
	private Pane rulesPane1;
	@FXML
	private Pane rulesPane2;
	@FXML
	private Pane rulesPane3;
	@FXML
	private ImageView item1;
	@FXML
	private Text itemDesc1;
	@FXML
	private ImageView item2;
	@FXML
	private Text itemDesc2;
	@FXML
	private ImageView item3;
	@FXML
	private Text itemDesc3;
	@FXML
	private ImageView item4;
	@FXML
	private Text itemDesc4;
	@FXML
	private ImageView item5;
	@FXML
	private Text itemDesc5;
	@FXML
	private ImageView item6;
	@FXML
	private Text itemDesc6;
	@FXML
	private ImageView item7;
	@FXML
	private Text itemDesc7;
	@FXML
	private ImageView item8;
	@FXML
	private Text itemDesc8;
	@FXML
	private ImageView item9;
	@FXML
	private Text itemDesc9;
	
	private ArrayList<Pane> rulesPanes;
	private int screen;
	private boolean itemsInit;
	
    public HelpController(){
    	MusicController.initUI();
    	rulesPanes = new ArrayList<Pane>();
    	screen = 1;
    	itemsInit = false;
    	// setScreen();
    }
    
    @FXML
    public void nextButtonClicked() throws IOException {
    	MusicController.playNext();
    	if(rulesPanes.isEmpty()) {
    		rulesPanes.add(rulesPane1);
        	rulesPanes.add(rulesPane2);
        	rulesPanes.add(rulesPane3);
    	}
    	if(screen < rulesPanes.size()) {
    		screen++;
    	}
    	if(screen == rulesPanes.size()) {
    		nextButton.setVisible(false);
    	}
    	setScreen();
    }
    
    /**
     * Called when the back button is clicked from the rules screen
     * @throws IOException
     */
    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
    	MusicController.playBack();
    	if(screen == 1) {
    		StartGameScreen.start();
    	} else {
    		screen--;
    		setScreen();
    	}
    }
    
    private void setScreen() {
    	for(Pane pane : rulesPanes) {
    		if(rulesPanes.get(screen-1) == pane) {
    			pane.setVisible(true);
    			title.setText("Rules (" + (screen) + "/" + rulesPanes.size() + ")");
    		} else {
    			pane.setVisible(false);
    		}
    	}
    	if(screen == rulesPanes.size() && !itemsInit) {
    		try {
				configItems();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private void configItems() throws FileNotFoundException, JSONException {
    	itemsInit = true;
    	ArrayList<ImageView> items = new ArrayList<ImageView>();
    	ArrayList<Text> itemsDesc = new ArrayList<Text>();
    	items.add(item1);
    	items.add(item2);
    	items.add(item3);
    	items.add(item4);
    	items.add(item5);
    	items.add(item6);
    	items.add(item7);
    	items.add(item8);
    	items.add(item9);
    	itemsDesc.add(itemDesc1);
    	itemsDesc.add(itemDesc2);
    	itemsDesc.add(itemDesc3);
    	itemsDesc.add(itemDesc4);
    	itemsDesc.add(itemDesc5);
    	itemsDesc.add(itemDesc6);
    	itemsDesc.add(itemDesc7);
    	itemsDesc.add(itemDesc8);
    	itemsDesc.add(itemDesc9);

    	String path = getClass().getClassLoader().getResource("boards/items.json").getPath().replaceAll("%20", " ");
        assert(!path.isEmpty());
        JSONObject itemsJson = new JSONObject(new JSONTokener(new FileReader(path)));
        JSONArray itemPool = itemsJson.getJSONArray("items");
		for(int i = 0; i < itemPool.length(); i++) {
			JSONObject arrayItem = itemPool.getJSONObject(i);
			String type = arrayItem.getString("type");
			int id = Item.ItemType.valueOf(type).ordinal();
	    	String description = arrayItem.getString("description");
	    	items.get(i).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/item" + id + ".png"))));
	    	itemsDesc.get(i).setText(description + ".");
		}
    	
    }
    
}