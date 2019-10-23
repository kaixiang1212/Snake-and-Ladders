package Test;

import org.junit.jupiter.api.Test;

import Model.GameEngine;
import Model.Player;
import javafx.util.Pair;


class TestDice {

    @Test
    void diceRolled6(){
        GameEngine engine = new GameEngine();
        engine.addPlayer(new Player("Player 1"));
        engine.addPlayer(new Player("Player 2"));
        Pair<Player, Integer> result = engine.rollDice();
        Player currentPlayer = result.getKey();

        while (result.getValue() != 6) {
            assert(currentPlayer != engine.getCurrentPlayer());
            currentPlayer = engine.getCurrentPlayer();
            result = engine.rollDice();
            currentPlayer = result.getKey();
        }
        assert(currentPlayer == engine.getCurrentPlayer());
    }
}
