package Controller;

import javafx.scene.image.ImageView;


public class GifController {
	

	private BoardController boardcontroller;
	
	
	public GifController(BoardController controller) {
		this.boardcontroller = controller;
	}
	
	public ImageView getGifView (String id) {
		// Call function from boardcontroller that returns the corresponding id String.
		String ladderId = "gif" + id;
		return this.boardcontroller.getGif(ladderId);
	}
	
	
	public ImageView getImgView (String id) {
		// Call function from boardcontroller that returns the corresponding id String.
		return this.boardcontroller.getImg(id);
	}
	
	/**
	 * This function will take in the ladderId of the ladder Entity in the current coordinate, and activate the corresponding gif of the stated Ladder.
	 * @param ladderId
	 */
	public void shakeLadder(ImageView ladderGif, ImageView ladderImg) {
		this.boardcontroller.shakeLadder(ladderGif, ladderImg);
	}

	
	public void stopShakeLadder(ImageView ladderGif, ImageView ladderImg) {
		this.boardcontroller.stopShakeLadder(ladderGif, ladderImg);
	}
	
	
	
	/**
	 * This function will take in the snakeId of the snake Entity in the current coordinate, and activate the corresponding gif of the stated Snake.
	 * @param snakeId
	 */
	public void wriggleSnake(String snakeId) {
		
	}
}
