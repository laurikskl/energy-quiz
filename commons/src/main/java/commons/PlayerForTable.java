package commons;

/**
 * This class is made so we can easily display the information for the leaderboard
 */
public class PlayerForTable {

    public String score;
    public String userName;
    public String place;

    /**
     * Constructor
     * @param score
     * @param userName
     * @param place
     */
    public PlayerForTable(String score, String userName, String place) {
        this.score = score;
        this.userName = userName;
        this.place = place;
    }

    /**
     * Getter for the score
     * @return
     */
    public String getScore() {
        return score;
    }

    /**
     * Getter for username
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for place
     * @return
     */
    public String getPlace() {
        return place;
    }
}
