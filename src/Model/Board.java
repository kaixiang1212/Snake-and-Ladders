package Model;

public class Board {

    private Dice dice;

    public Board(){
    }

    public int rollDice(Player currentPlayer){
        int result = dice.roll();
        increment(currentPlayer, result);
        return -1;
    }

    public int increment(Player currentPlayer, int number){
        return -1;
    }
}
