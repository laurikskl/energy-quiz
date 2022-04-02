package commons;

import java.util.ArrayList;
import java.util.Objects;

public class Game {

    public Type type;
    public Screen screen;
    private long id;
    private int round;
    private ArrayList<Player> players;
    private ArrayList<Question> questions; //We insert only one question here

    /**
     * Create a Game Object
     *
     * @param id        - an id used to differentiate between games when running multiple ones concurrently
     * @param players   - the users participating in the game
     * @param questions - the list of questions used in the current game
     */
    public Game(long id, ArrayList<Player> players, ArrayList<Question> questions) {
        this.id = id;
        this.players = players;
        this.questions = questions;
        this.round = 0;
    }

    /**
     * Basic constructor
     */
    public Game() {
    }

    /**
     * Getter for the id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for round
     *
     * @return round
     */
    public int getRound() {
        return round;
    }

    /**
     * Setter for round
     *
     * @param round
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Getter for players
     *
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Setter for player
     *
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Getter for the questions
     *
     * @return questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Equals method.
     *
     * @param o an object
     * @return boolean if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return id == game.id && round == game.round && Objects.equals(players, game.players) &&
                Objects.equals(questions, game.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round, players, questions);
    }
}
