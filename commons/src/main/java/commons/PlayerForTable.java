package commons;

/**
 * For making the player be displayable in the leaderboard.
 */
public class PlayerForTable {

  public String score;
  public String userName;
  public String place;

  /**
   * Constructor.
   * @param score Score as a string
   * @param userName Just the username
   * @param place Place as a string
   */
  public PlayerForTable(String score, String userName, String place) {
    this.score = score;
    this.userName = userName;
    this.place = place;
  }

  public String getScore() {
    return score;
  }

  public String getUserName() {
    return userName;
  }

  public String getPlace() {
    return place;
  }
}
