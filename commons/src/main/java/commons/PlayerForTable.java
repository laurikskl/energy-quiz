package commons;

public class PlayerForTable {

  public String score;
  public String userName;
  public String place;

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
