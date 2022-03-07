package server.Player;

import commons.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    public PlayerController(PlayerService playerService, PlayerRepository playerRepository) {
        this.playerService = playerService;
        this.playerRepository=playerRepository;
    }

    @GetMapping(path = { "", "/" })
    public List<Player> getAll(){
        return playerService.getPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") long id) {
        if (id<0 || !playerRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(playerRepository.getById(id));
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Player> add(@RequestBody Player player) {

        List<Player> allPlayers = playerRepository.findAll();
        boolean usernameAlreadyExists = false;

        if (isNullOrEmpty(player.userName)) {
            return ResponseEntity.badRequest().build();
        }
        for (int i=0; i<allPlayers.size(); i++){
            if(allPlayers.get(i).userName.equals(player.userName)) usernameAlreadyExists=true;
        }
        if (usernameAlreadyExists) {
            return ResponseEntity.badRequest().build();
        }
        Player saved = playerRepository.save(player);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
