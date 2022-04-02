package commons;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Objects;

/**
 * This is the class for the Player entity in the database.
 */
@Entity
public class RoundPlayer {

    /**
     * The player has a long unique ID, which is the primary key.
     * A String which contains the username of the Player.
     * An int that stores the score of the Player.
     * And an int that stores in which round the player is
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(unique = true)
    public String userName;
    public int round;
    public int score;

    @SuppressWarnings("unused")
    private RoundPlayer() {
        // for object mapper
    }

    /**
     * Create a Player Object.
     *
     * @param userName - the userName of the player
     * @param score    - his highest score
     */
    public RoundPlayer(String userName, int score, int round) {
        this.userName = userName;
        this.score = score;
        this.round = round;
    }

    /**
     * Getter for the userName of the object Player.
     *
     * @return the userName of the object Player
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the userName of the object Player.
     *
     * @param newUserName - the new userName for the object Player
     */
    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    /**
     * Getter for the score of the object Player.
     *
     * @return the score of the object Player
     */
    public long getScore() {
        return score;
    }

    /**
     * Setter for the score of the object Player.
     *
     * @param score - the new score for the object Player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter for round number
     *
     * @return the round number
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
