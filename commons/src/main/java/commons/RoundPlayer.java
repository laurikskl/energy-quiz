package commons;

public class RoundPlayer extends Player {

    //Fields
    private int round;

    /**
     * Create a Player Object.
     *
     * @param userName - the userName of the player
     * @param score    - his highest score
     */
    public RoundPlayer(String userName, int score, int round) {
        super(userName, score);
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

}
