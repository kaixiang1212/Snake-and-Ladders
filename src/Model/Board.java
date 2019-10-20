package Model;

public class Board {

    private Dice dice;

    public Board(){
        this.dice = new Dice();
    }

    public int rollDice(Player currentPlayer){
        int result = dice.roll();
        increment(currentPlayer, result);
        return result;
    }

    public void increment(Player currentPlayer, int number){
        currentPlayer.setPosition(currentPlayer.getPosition() + number);
        System.out.println(currentPlayer.getPlayerName() + " incremented by " + number + ", position: " +
                currentPlayer.getPosition());
    }
}
