package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerTest {

  @Test
  public void checkConstructor() {
    Player player = new Player("Adda", 6969);
    assertEquals("Adda", player.userName);
    assertEquals(6969, player.score);
  }

  @Test
  public void getName() {
    Player player = new Player("Adda", 6969);
    assertEquals(player.getUserName(), "Adda");
  }

  @Test
  public void getScore() {
    Player player = new Player("Adda", 6969);
    assertEquals(player.getScore(), 6969);
  }

  @Test
  public void setUserName() {
    Player player = new Player("Adda", 6969);
    player.setUserName("Demi");
    assertEquals(player.getUserName(), "Demi");
  }

  @Test
  public void setScore() {
    Player player = new Player("Adda", 6969);
    player.setScore(0);
    assertEquals(player.getScore(), 0);
  }

  @Test
  public void toStringTest() {
    Player player = new Player("Adda", 6969);
    assertEquals(player.toString(), "Username: Adda" + "\n" +
        "Highest score: 6969");

  }

  @Test
  public void equalsTest() {
    Player player1 = new Player("Adda", 6969);
    Player player2 = new Player("Adda", 6969);
    Player player3 = new Player("Demi", 0);
    assertEquals(player1, player1);
    assertEquals(player1, player2);
    assertNotEquals(player1, player3);
  }
}
