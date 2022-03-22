package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerForTableTest {

    PlayerForTable player;

    @BeforeEach
    public void initialize() {
        player = new PlayerForTable("5454", "KillerRonaldoKing", "7");
    }

    @Test
    void getScore() {
        assertEquals(player.getScore(), "5454");
    }

    @Test
    void getUserName() {
        assertEquals(player.getUserName(), "KillerRonaldoKing");
    }

    @Test
    void getPlace() {
        assertEquals(player.getPlace(), "7");
    }
}