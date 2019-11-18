package Controller;

import Model.GameEngine;
import javafx.animation.AnimationTimer;

public class AnimationController {
	private static final int maxFrames = 300;
	private static final int animationFrames = 10;
	private static final long frametime = 8333333;
	
    private static int frame;
    private static boolean isSpinning;
    private static boolean isPlayerMoving;
    private static boolean poisoned;
    private static long lastTime;

    
    private static DiceController diceController;
    
    public AnimationController(DiceController dc) {
    	frame = 0;
    	isSpinning = false;
    	isPlayerMoving = false;
    	poisoned = false;
    	frame = 0;
    	diceController = dc;
    	
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
        		int diceFrame = 1;
        		if (poisoned) {
        			diceFrame = (int) (Math.random() * 3) + 1;
        		}  else {
        			diceFrame = (int) (Math.random() * 6) + 1;
        		}
        		if (frame % 6 == 0) {
        			diceController.draw(diceFrame);
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
}
