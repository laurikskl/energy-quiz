package commons;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

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

    /**
     * Getter for round number
     *
     * @return
     */
    public int getRound() {
        return round;
    }

    /**
     * Setter for round number
     *
     * @param round - new round number
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Equals method for the RoundPlayer object
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundPlayer that = (RoundPlayer) o;
        return id == that.id && round == that.round && score == that.score && Objects.equals(userName, that.userName);
    }

    /**
     * Hash function for the RoundPlayer object.
     *
     * @return the object in hash form
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Returns a human-readable form of the player object.
     *
     * @return a string with the human-readable form of the object.
     */
    @Override
    public String toString() {
        return "RoundPlayer{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", round=" + round +
                ", score=" + score +
                '}';
    }
}
