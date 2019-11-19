package Controller;

import Model.GameEngine;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class AnimationController {
	private static final int maxFrames = 300;
	private static final int animationFrames = 10;
	private static final long frametime = 8333333;

    private static int frame;
    private static boolean isSpinning;
    private static boolean isPlayerMoving;
    private static boolean poisoned;
    private static long lastTime;
    private static int diceFrame = 1;

    private static DiceController diceController;
    private static BoardController boardController;

    public AnimationController(DiceController dc, BoardController bc) {
    	frame = 0;
    	isSpinning = false;
    	isPlayerMoving = false;
    	poisoned = false;
    	frame = 0;
    	diceController = dc;
    	boardController = bc;
    }


    /**
     * Randomise Dice face for maxFrames frames
     * after maxFrames frames it automatically stops
     *
     * When a player is moving, move their token one position every animationFrames frames
     * once the player reaches their destination, update the game state and prepare for next roll
     */
    private static AnimationTimer animation = new AnimationTimer() {
        public void handle(long time) {
        	if((time - lastTime) < frametime) {
        		return;
        	}
        	int currentPos = diceController.getCurrentPos();
        	int destination = diceController.getDestination();

        	if (isSpinning) {
        		if (frame % 6 == 0) {
        			int num;
        			int maxNum = 6;
        			if(poisoned)
        				maxNum = 3;
        			
        			num = (int) (Math.random() * maxNum) + 1;
        			while(diceFrame == num) {
        				num = (int) (Math.random() * maxNum) + 1;
        			}
        			diceFrame = num;
        			diceController.draw(diceFrame, poisoned);
        		}

            	if(frame == maxFrames) {
            		diceController.stopButtonClicked();
            	}
        	} else if (isPlayerMoving) {
        		if (frame%animationFrames == 0 && currentPos == destination) {
        			diceController.prepareNextTurn();
        			GameEngine.getCurrentPlayer().updatePoison();
        		} else if (frame%animationFrames == 0 && currentPos <= destination) {
        			GameEngine.updatePosition(GameEngine.getCurrentPlayer(), currentPos + 1);
        			boardController.cleanPickedUpItems();
        		}
        	}
        	frame++;
        	lastTime = time;
        }
    };

    /**
     * Return the AnimationTimer object of the class
     * @return animation: AnimationTimer object
     */
    public static AnimationTimer getAnimation() {
    	return animation;
    }

    /**
     * Sets whether the dice should be currently spinning or not
     * @param value: true if dice should currently be spinning
     * 				 false otherwise
     */
    public static void setSpinning(boolean value) {
    	frame = 0;
    	isSpinning = value;
    }

    /**
     * Sets whether current player should be moving or not currently
     * @param value: true if current player should currently be moving
     * 			     false otherwise
     */
    public static void setPlayerMoving(boolean value) {
    	frame = 0;
    	isPlayerMoving = value;
    }

    public static void setPoison(boolean status) {
    	poisoned = status;
    }
    
	public static ImageView getGifView (String id) {
		// Call function from boardcontroller that returns the corresponding id String.
		String ladderId = "gif" + id;
		return boardController.getGif(ladderId);
	}
	
	public static ImageView getImgView (String id) {
		// Call function from boardcontroller that returns the corresponding id String.
		return boardController.getImg(id);
	}
	
	/**
	 * This function will take in the ladderId of the ladder Entity in the current coordinate, and activate the corresponding gif of the stated Ladder.
	 * @param ladderId
	 */
	public static void shakeLadder(ImageView ladderGif, ImageView ladderImg) {
		boardController.shakeLadder(ladderGif, ladderImg);
	}
	
	public static void stopShakeLadder(ImageView ladderGif, ImageView ladderImg) {
		boardController.stopShakeLadder(ladderGif, ladderImg);
	}
	
	/**
	 * This function will take in the snakeId of the snake Entity in the current coordinate, and activate the corresponding gif of the stated Snake.
	 * @param snakeId
	 */
	public static void wriggleSnake(String snakeId) {
		
	}

}
