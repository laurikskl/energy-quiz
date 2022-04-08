package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundPlayerTest {

  RoundPlayer player;
  RoundPlayer player2;

  @BeforeEach
  void setup(){
    this.player = new RoundPlayer("Adda", 6969, 3);
    this.player2 = new RoundPlayer("Adda", 6969, 3);

  }

  @Test
  void getRound() {
    assertEquals(player.getRound(), 3);
  }

  @Test
  void setRound() {
    player.setRound(4);
    assertEquals(player.getRound(), 4);
  }

  @Test
  void testEquals() {

  }

  @Test
  void testHashCode() {
    assertEquals(player.hashCode(), player2.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(player.toString(),"RoundPlayer{id=0, userName='Adda', round=3, score=6969}");
  }
}