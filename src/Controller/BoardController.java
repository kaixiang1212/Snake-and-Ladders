package Controller;

import Model.*;
import View.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

	private List<Pair<Entity, ImageView>> initialEntities;
    private GameEngine engine;
	private Stage stage;
	private GameScreen gamescreen;

	public BoardController() {}

    /**
     * Configuration for Board Controller
     * and configure Dice Controller
     * @param engine Game Engine
     * @param initialEntities
     * @param s Stage
     * @param game Game Screen
     */
	public void config(GameEngine engine, List<Pair<Entity, ImageView>> initialEntities, Stage s, GameScreen game) {
		this.engine = engine;
		this.initialEntities = new ArrayList<>(initialEntities);
		this.stage = s;
		this.gamescreen = game;
		diceController.config(engine);
	}

	@FXML
	public void init() {
		int lastrand = 0, rand = 0;
		for (int y = 0; y < engine.getBoard().getHeight(); y++) {
			for (int x = 0; x < engine.getBoard().getWidth(); x++) {
				while (rand == lastrand)
					rand = (int) (Math.random() * 6);
				Image boardFloor = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/gametile" + rand + ".png")));
				ImageView floorView = new ImageView(boardFloor);
				floorView.setFitHeight(gamescreen.getHeight() / (float) engine.getBoard().getHeight());
				floorView.setFitWidth(gamescreen.getHeight() / (float) engine.getBoard().getWidth());
				squares.add(floorView, x, y);
				Text tilenum = new Text(Integer.toString(engine.getBoard().getPosition(x, engine.getBoard().getHeight() - y - 1)));
				tilenum.setFont(Font.font("Harlow Solid Italic", 48));
				tilenum.setFill(Color.WHITE);
				tilenum.setStroke(Color.BLACK);
				tilenum.setStrokeWidth(3);
				squares.add(tilenum, x, y);
				GridPane.setHalignment(tilenum, HPos.CENTER);
				//GridPane.setValignment(tilenum, VPos.TOP);
				lastrand = rand;
			}
		}

		// Order entities by their order in the enum Type (in Model.Entity)
		initialEntities.sort(new Comparator<Pair<Entity, ImageView>>() {
			@Override
			public int compare(Pair<Entity, ImageView> p1, Pair<Entity, ImageView> p2) {
				return p2.getKey().getEntityType().ordinal() - p1.getKey().getEntityType().ordinal();
			}
		});

		for (Pair<Entity, ImageView> entityPair : initialEntities) {
			Entity entity = entityPair.getKey();
			ImageView entityImage = entityPair.getValue();
			squares.getChildren().add(entityImage);
			GridPane.setHalignment(entityImage, HPos.CENTER);
			if (entity instanceof Snake || entity instanceof Ladder) {
				addSegments(entity);
			}
		}
	}

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

	public void addLadder(Ladder ladder) {

	}

}