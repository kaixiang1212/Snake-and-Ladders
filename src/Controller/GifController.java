package Controller;

import javafx.scene.image.ImageView;


public class GifController {

	private static BoardController boardcontroller;	
	
	public GifController(BoardController controller) {
		boardcontroller = controller;
	}
	
	public static ImageView getGifView (String id) {
		// Call function from boardcontroller that returns the corresponding id String.
		String ladderId = "gif" + id;
		return boardcontroller.getGif(ladderId);
	}
	
	public static ImageView getImgView (String id) {
		// Call function from boardcontroller that returns the corresponding id String.
		return boardcontroller.getImg(id);
	}
	
	/**
	 * This function will take in the ladderId of the ladder Entity in the current coordinate, and activate the corresponding gif of the stated Ladder.
	 * @param ladderId
	 */
	public static void shakeLadder(ImageView ladderGif, ImageView ladderImg) {
		boardcontroller.shakeLadder(ladderGif, ladderImg);
	}
	
	public static void stopShakeLadder(ImageView ladderGif, ImageView ladderImg) {
		boardcontroller.stopShakeLadder(ladderGif, ladderImg);
	}
	
	/**
	 * This function will take in the snakeId of the snake Entity in the current coordinate, and activate the corresponding gif of the stated Snake.
	 * @param snakeId
	 */
	public static void wriggleSnake(String snakeId) {
		
	}
}
