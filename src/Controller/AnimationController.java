package Controller;

import Model.GameEngine;
import javafx.animation.AnimationTimer;

public class AnimationController {
	private final int maxFrames = 600;
	private final int animationFrames = 10;
	
    private int frame;
    private boolean isSpinning;
    private boolean isPlayerMoving;
    
    private GameEngine engine;
    private DiceController diceController;
    
    public AnimationController(GameEngine engine, DiceController diceController) {
    	isSpinning = false;
    	isPlayerMoving = false;
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
        	int currentPos = diceController.getCurrentPos();
        	int destination = diceController.getDestination();
        	
        	if (isSpinning) {
        		int diceFrame = (int) (Math.random() * 6) + 1;
        		diceController.draw(diceFrame);
            	frame++;
            	if(frame == maxFrames) {
            		diceController.stopButtonClicked();
            	}
        	} else if (isPlayerMoving) {		
        		if (frame%animationFrames == 0 && currentPos == destination) {
        			diceController.idle();
        		} else if (frame%animationFrames == 0 && currentPos <= destination) {
        			engine.updatePosition(engine.getCurrentPlayer(), currentPos + 1);
        		}
        		frame++;
        	}
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
}
