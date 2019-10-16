package Model;

public class HumanPlayer extends Player {

//    private inputListener inputListener;

    public HumanPlayer(String playerName) {
        super(playerName);
//        this.inputListener = new inputListener()
    }

    // TODO
    @Override
    public PlayerMoves getMove() {
        return PlayerMoves.ROLL;
    }

}
