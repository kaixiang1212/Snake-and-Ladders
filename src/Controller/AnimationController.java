package Controller;

import Model.GameEngine;
import javafx.animation.AnimationTimer;

public class AnimationController {
	private final int maxFrames = 300;
	private final int animationFrames = 10;
	private final long frametime = 8333333;
	
    private int frame;
    private boolean isSpinning;
    private boolean isPlayerMoving;
    private boolean poisoned;
    
    private GameEngine engine;
    private DiceController diceController;
    private long lastTime;
    
    public AnimationController(GameEngine engine, DiceController diceController) {
    	isSpinning = false;
    	isPlayerMoving = false;
    	poisoned = false;
    	frame = 0;
    	this.engine = engine;
    	this.diceController = diceController;
    }
    
    public void setEngine(GameEngine engine) {
    	this.engine = engine;
    }
    
    /**
     * Randomise Dice face for maxFrames frames
     * after maxFrames frames it automatically stops
     * 
     * When a player is moving, move their token one position every animationFrames frames
     * once the player reaches their destination, update the game state and prepare for next roll
     */
    private AnimationTimer animation = new AnimationTimer() {
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
        			engine.getCurrentPlayer().updatePoison();
        		} else if (frame%animationFrames == 0 && currentPos <= destination) {
        			engine.updatePosition(engine.getCurrentPlayer(), currentPos + 1);
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
    public AnimationTimer getAnimation() {
    	return animation;
    }
    
    /**
     * Sets whether the dice should be currently spinning or not
     * @param value: true if dice should currently be spinning
     * 				 false otherwise
     */
    public void setSpinning(boolean value) {
    	frame = 0;
    	isSpinning = value;
    }
    
    /**
     * Sets whether current player should be moving or not currently
     * @param value: true if current player should currently be moving
     * 			     false otherwise
     */
    public void setPlayerMoving(boolean value) {
    	frame = 0;
    	isPlayerMoving = value;
    }
    
    public void setPoison(boolean status) {
    	poisoned = status;
    }
}
