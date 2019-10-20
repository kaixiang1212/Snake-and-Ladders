package Test;

import Model.Board;
import Model.Multiplayer;
import Model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestDice {

    @Test
    void diceRolled6(){
        Multiplayer players = new Multiplayer(null, new Board());
        players.addPlayer(new Player("Player 1"));
        players.addPlayer(new Player("Player 2"));
        Player currentPlayer = players.getCurrentPlayer();
        while (players.rollDice() != 6) {
            assertNotSame(currentPlayer, players.getCurrentPlayer());
            currentPlayer = players.getCurrentPlayer();
        }
        assertSame(currentPlayer, players.getCurrentPlayer());
    }
}
