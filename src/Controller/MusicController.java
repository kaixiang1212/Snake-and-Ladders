package Controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicController {
    private static boolean initUI = false;
    private static MediaPlayer backBtnPlayer;
    private static MediaPlayer switchBtnPlayer;
    private static MediaPlayer nextBtnPlayer;

    private static boolean initDice = false;
    private static MediaPlayer diceRollPlayer;
    private static MediaPlayer diceThrowPlayer1;
    private static MediaPlayer diceThrowPlayer2;
    private static MediaPlayer dice6Player;

    private static boolean initGame = false;
    private static MediaPlayer descendPlayer;
    private static MediaPlayer ascendPlayer;
    private static MediaPlayer snakePlayer1;
    private static MediaPlayer snakePlayer2;
    private static MediaPlayer victoryPlayer;
    private static MediaPlayer movePlayer;
    private static MediaPlayer itemAppearPlayer;
    private static MediaPlayer itemDisappearPlayer;
    private static MediaPlayer itemPickupPlayer;

    private static boolean initBoard = false;
    private static MediaPlayer bgMusicPlayer;
    
    private static boolean soundFxOn = true;
    private static boolean musicOn = true;

    public MusicController(){ }

    /**
     * Initialise UI component Sound (Next Button/Back Button)
     */
    public static void initUI(){
        if(initUI) return;
    	initUI = true;
        Media backBtnSound = new Media(new File("src/asset/Sound/back.mp3").toURI().toString());
        Media switchBtnSound = new Media(new File("src/asset/Sound/switch.mp3").toURI().toString());
        Media nextBtnSound = new Media(new File("src/asset/Sound/next.mp3").toURI().toString());

        backBtnPlayer = new MediaPlayer(backBtnSound);
        switchBtnPlayer = new MediaPlayer(switchBtnSound);
        nextBtnPlayer = new MediaPlayer(nextBtnSound);
    }

    /**
     * Initialise Dice Sound
     */
    public static void initDice(){
    	if(initDice) return;
        initDice = true;
        Media diceRoll = new Media(new File("src/asset/Sound/diceRoll.mp3").toURI().toString());
        Media diceThrow1 = new Media(new File("src/asset/Sound/diceThrow1.mp3").toURI().toString());
        Media diceThrow2 = new Media(new File("src/asset/Sound/diceThrow2.mp3").toURI().toString());
        Media dice6Sound = new Media(new File("src/asset/Sound/rolled6.mp3").toURI().toString());
        
        diceRollPlayer = new MediaPlayer(diceRoll);
        diceThrowPlayer1 = new MediaPlayer(diceThrow1);
        diceThrowPlayer2 = new MediaPlayer(diceThrow2);
        dice6Player = new MediaPlayer(dice6Sound);

        diceRollPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                diceRollPlayer.seek(Duration.ZERO);
                diceRollPlayer.play();
            }
        });
    }

    /**
     * Initialise Game Sound (Movement/Snake/Ladders/Victory)
     */
    public static void initGame(){
        if(initGame) return;
    	initGame = true;
        Media ascendSound = new Media(new File("src/asset/Sound/ascend.mp3").toURI().toString());
        Media descendSound = new Media(new File("src/asset/Sound/descend.mp3").toURI().toString());
        Media snakeSound1 = new Media(new File("src/asset/Sound/snake1.mp3").toURI().toString());
        Media snakeSound2 = new Media(new File("src/asset/Sound/snake2.mp3").toURI().toString());
        Media victorySound = new Media(new File("src/asset/Sound/win.mp3").toURI().toString());
        Media moveSound = new Media(new File("src/asset/Sound/move.mp3").toURI().toString());
        Media itemAppearSound = new Media(new File("src/asset/Sound/itemappear.mp3").toURI().toString());
        Media itemDisappearSound = new Media(new File("src/asset/Sound/itemdisappear.mp3").toURI().toString());
        Media itemPickupSound = new Media(new File("src/asset/Sound/itempickup.mp3").toURI().toString());

        ascendPlayer = new MediaPlayer(ascendSound);
        descendPlayer = new MediaPlayer(descendSound);
        snakePlayer1 = new MediaPlayer(snakeSound1);
        snakePlayer2 = new MediaPlayer(snakeSound2);
        victoryPlayer = new MediaPlayer(victorySound);
        movePlayer = new MediaPlayer(moveSound);
        itemAppearPlayer = new MediaPlayer(itemAppearSound);
        itemDisappearPlayer = new MediaPlayer(itemDisappearSound);
        itemPickupPlayer = new MediaPlayer(itemPickupSound);
    }
    
    /**
     * Initialise background sounds
     */
    public static void initBoard(){
        if(initBoard) return;
    	initBoard = true;
        Media bgMusic = new Media(new File("src/asset/Sound/bgMusic.mp3").toURI().toString());
        bgMusicPlayer = new MediaPlayer(bgMusic);
        bgMusicPlayer.setOnEndOfMedia(new Runnable() {
        	@Override
            public void run() {
                bgMusicPlayer.seek(Duration.ZERO);
                bgMusicPlayer.play();
            }
        });
    }

    /**
     * Stops all ongoing sounds or any not stopped sounds
     */
    public static void clear(){
        if (initUI) {
            backBtnPlayer.stop();
            switchBtnPlayer.stop();
            nextBtnPlayer.stop();
        }
        if (initDice){
            diceThrowPlayer1.stop();
            diceThrowPlayer2.stop();
            diceRollPlayer.stop();
            dice6Player.stop();
        }
        if (initGame){
            ascendPlayer.stop();
            descendPlayer.stop();
            snakePlayer1.stop();
            snakePlayer2.stop();
            movePlayer.stop();
            itemAppearPlayer.stop();
            itemDisappearPlayer.stop();
            itemPickupPlayer.stop();
        }
    }
    
    public static void playNext(){
        if (!initUI) return;
        clear();
        if(soundFxOn)
        	nextBtnPlayer.play();
    }

    public static void playBack(){
        if (!initUI) return;
        clear();
        if(soundFxOn)
        	backBtnPlayer.play();
    }

    public static void playSwitch(){
        clear();
        if (!initUI) return;
        if(soundFxOn)
        	switchBtnPlayer.play();
    }

    public static void playThrowDice(){
        if (!initDice) return;
        diceThrowPlayer1.stop();
        diceThrowPlayer2.stop();
        int rand = (int)(Math.random() * 2) + 1;
        if (rand == 1) {
        	if(soundFxOn)
        		diceThrowPlayer1.play();
        }
        else if (rand == 2) {
        	if(soundFxOn)
        		diceThrowPlayer2.play();
        }
    }

    public static void playRollDice(){
        if (!initDice) return;
        if(soundFxOn)
        	diceRollPlayer.play();
    }

    public static void playRolled6(){
        if (!initDice) return;
        if(soundFxOn)
        	dice6Player.play();
    }
    
    public static void playMove(){
        if (!initGame) return;
        movePlayer.seek(Duration.ZERO);
        movePlayer.setVolume(0.2);
        if(soundFxOn)
        	movePlayer.play();
    }
    
    public static void playItemAppear() {
    	if (!initGame) return;
        itemAppearPlayer.setVolume(1.0);
        if(soundFxOn)
        	itemAppearPlayer.play();
    }
    
    public static void playItemDisappear() {
    	if (!initGame) return;
        itemDisappearPlayer.setVolume(1.0);
        if(soundFxOn)
        	itemDisappearPlayer.play();
    }
    
    public static void playItemPickup() {
    	if (!initGame) return;
        itemPickupPlayer.setVolume(1.0);
        clear();
        if(soundFxOn)
        	itemPickupPlayer.play();
    }

    public static void playSnake(){
        if (!initGame) return;
        if(soundFxOn)
        	descendPlayer.play();
        int rand = (int) (Math.random() * 2) + 1;
        if (rand == 1){
        	if(soundFxOn)
        		snakePlayer1.play();
        } else {
        	if(soundFxOn)
        		snakePlayer2.play();
        }
    }

    public static void playLadder(){
        if (!initGame) return;
        if(soundFxOn)
        	ascendPlayer.play();
    }

    public static void playVictory(){
        if (!initGame) return;
        clear();
        if(soundFxOn)
        	victoryPlayer.play();
    }

    public static void playBGM(){
        if (!initBoard) return;
        bgMusicPlayer.setVolume(0.1);
        if(musicOn)
        	bgMusicPlayer.play();
    }
    
    public static void stopBGM(){
        if (!initBoard) return;
        bgMusicPlayer.stop();
    }
    
    public static void togglefx() {
    	soundFxOn = !soundFxOn;
    }
    
    public static void toggleMusic() {
    	musicOn = !musicOn;
    	if(musicOn) {
    		playBGM();
    	} else {
    		stopBGM();
    	}
    }
    
    public static boolean getFxToggle() {
    	return soundFxOn;
    }
    
    public static boolean getMusicToggle() {
    	return musicOn;
    }
}
