package server.Player;

import commons.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Player.PlayerRepository;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerRepository players;

    public PlayerController(PlayerRepository players) {
        this.players = players;
    }

    @GetMapping(path = { "", "/" })
    public List<Player> getAll(){
        return players.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Player> getByUsername(@PathVariable("username") String username) {
        if (username==null || username.equals(" ") || !players.existsById(username)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(players.getById(username));
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Player> add(@RequestBody Player player) {

        if (isNullOrEmpty(player.userName)) {
            return ResponseEntity.badRequest().build();
        }

        Player saved = players.save(player);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
