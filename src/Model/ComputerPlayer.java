package Model;

public class ComputerPlayer extends Player {

    ComputerPlayer(String playerName) {
        super(playerName);
    }

    // TODO::
    @Override
    public PlayerMoves getMove() {
        return PlayerMoves.ROLL;
    }
}
