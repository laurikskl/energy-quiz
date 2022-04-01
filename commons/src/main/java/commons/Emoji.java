package commons;

/**
 * Class for emojis
 * Used in HTTP requests where emojis will be sent and received
 */

public class Emoji {

    /**
     * An emoji stores the sender of the emoji and which emoji was sent
     */

    private Player sender;
    private String emoji;


    /**
     * Zero-arg constructor
     */
    public Emoji() {

    }


    /**
     * @param sender the player who sent the emoji
     * @param emoji what kind of emoji was sent
     */

    public Emoji(Player sender, String emoji) {
        this.sender = sender;
        this.emoji = emoji;
    }


    /**
     * @return sender of an emoji
     */

    public Player getSender() {
        return sender;
    }


    /**
     * @param sender - the sender of an emoji
     */

    public void setSender(Player sender) {
        this.sender = sender;
    }


    /**
     * @return kind of emoji sent
     */

    public String getEmoji() {
        return emoji;
    }


    /**
     * @param emoji - kind of emoji sent
     */

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

}
