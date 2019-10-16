package Model;

import java.util.Random;

public class Dice {

    private Random r;

    public Dice(){
        this.r = new Random();
    }

    /**
     * Roll a dice and get a random number between 1 and 6
     * @return a random number between 1 and 6
     */
    public int roll(){
        return r.nextInt(6) + 1;
    }
}
