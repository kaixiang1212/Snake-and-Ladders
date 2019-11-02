package Controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicController {
    private boolean initUI = false;
    private MediaPlayer backBtnPlayer;
    private MediaPlayer switchBtnPlayer;
    private MediaPlayer nextBtnPlayer;

    private boolean initDice = false;
    private MediaPlayer diceRollPlayer;
    private MediaPlayer diceThrowPlayer1;
    private MediaPlayer diceThrowPlayer2;
    private MediaPlayer dice6Player;

    private boolean initGame = false;
    private MediaPlayer descendPlayer;
    private MediaPlayer ascendPlayer;
    private MediaPlayer snakePlayer1;
    private MediaPlayer snakePlayer2;
    private MediaPlayer victoryPlayer;


    public MusicController(){ }

    /**
     * Initialise UI component Sound (Next Button/Back Button)
     */
    public void initUI(){
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
    public void initDice(){
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
     * Initialise Game Sound (Snake/Ladders/Victory)
     */
    public void initGame(){
        initGame = true;
        Media ascendSound = new Media(new File("src/asset/Sound/ascend.mp3").toURI().toString());
        Media descendSound = new Media(new File("src/asset/Sound/descend.mp3").toURI().toString());
        Media snakeSound1 = new Media(new File("src/asset/Sound/snake1.mp3").toURI().toString());
        Media snakeSound2 = new Media(new File("src/asset/Sound/snake2.mp3").toURI().toString());
        Media victorySound = new Media(new File("src/asset/Sound/win.mp3").toURI().toString());

        ascendPlayer = new MediaPlayer(ascendSound);
        descendPlayer = new MediaPlayer(descendSound);
        snakePlayer1 = new MediaPlayer(snakeSound1);
        snakePlayer2 = new MediaPlayer(snakeSound2);
        victoryPlayer = new MediaPlayer(victorySound);
    }

    /**
     * Stops all ongoing sounds or any not stopped sounds
     */
    public void clear(){
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
        }
    }

    public void playNext(){
        if (!initUI) return;
        clear();
        nextBtnPlayer.play();
    }

    public void playBack(){
        if (!initUI) return;
        clear();
        backBtnPlayer.play();
    }

    public void playSwitch(){
        clear();
        if (!initUI) return;
        switchBtnPlayer.play();
    }

    public void playThrowDice(){
        if (!initDice) return;
        diceThrowPlayer1.stop();
        diceThrowPlayer2.stop();
        int rand = (int )(Math.random() * 2) + 1;
        if (rand == 1) {
            diceThrowPlayer1.play();
        }
        else if (rand == 2) {
            diceThrowPlayer2.play();
        }
    }

    public void playRollDice(){
        if (!initDice) return;
        clear();
        diceRollPlayer.play();
    }

    public void playRolled6(){
        if (!initDice) return;
        dice6Player.play();
    }

    public void playSnake(){
        if (!initGame) return;
        clear();
        descendPlayer.play();
        int rand = (int) (Math.random() * 2) + 1;
        if (rand == 1){
            snakePlayer1.play();
        } else {
            snakePlayer2.play();
        }
    }

    public void playLadder(){
        if (!initGame) return;
        clear();
        ascendPlayer.play();
    }

    public void playVictory(){
        if (!initGame) return;
        clear();
        victoryPlayer.play();
    }
}
