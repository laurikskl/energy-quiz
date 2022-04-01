package server.Emoji;

import commons.Emoji;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for receiving and sending emojis between client and server.
 */

@RestController
@RequestMapping("/emojis")
public class EmojiController {

    /**
     * Until multiplayer is functional, this method only prints the sender and kind of emoji received
     *
     * @param emoji The emoji sent by a player
     * @return response entity of the emoji received
     */
    @PostMapping("/sent")
    public ResponseEntity<Emoji> receiveEmoji(@RequestBody Emoji emoji) {
        System.out.println("Sender: " + emoji.getSender().toString());
        System.out.println("Emoji: " + emoji.getEmoji());
        return ResponseEntity.ok(emoji);
    }
}
