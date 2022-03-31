package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Emoji
 */

class EmojiTest {

    /**
     * Fields used in setup
     */

    private Emoji emoji;
    private Player sender;


    /**
     * Setup before each test
     * create a player and an emoji
     */

    @BeforeEach
    void setUp() {
        sender = new Player("Max", 9000);
        emoji = new Emoji(sender, "trophy");
    }


    /**
     * Make sure constructor creates instance of Emoji
     */

    @Test
    void constructorTest() {
        assertNotNull(emoji);
        assertTrue(emoji instanceof Emoji);
    }


    /**
     * Testing getter for Sender
     */

    @Test
    void getSender() {
        assertEquals(sender, emoji.getSender());
    }


    /**
     * Testing setter for Sender
     */

    @Test
    void setSender() {
        Player other = new Player("Dex", 0);
        emoji.setSender(other);
        assertEquals(other, emoji.getSender());
    }


    /**
     * Testing getter for Emoji
     */

    @Test
    void getEmoji() {
        assertEquals("trophy", emoji.getEmoji());
    }


    /**
     * Testing setter for Emoji
     */

    @Test
    void setEmoji() {
        emoji.setEmoji("dead");
        assertEquals("dead", emoji.getEmoji());
    }

}