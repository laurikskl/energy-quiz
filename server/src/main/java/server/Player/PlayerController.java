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
        this.playerRepository = playerRepository;
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

    @PostMapping(path = "/setPlayer")
    public ResponseEntity<Player> setPlayer(@RequestBody Player player) {
        //check if player with name already in database, if so delete it
        for(Player toComp : playerRepository.findAll()) {
            if(toComp.getUserName().equals(player.getUserName())) {
                playerRepository.deleteById(toComp.id);
            }
        }
        //save the player with the new score to the database
        playerRepository.save(player);
        return ResponseEntity.ok(player);
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Player> add(@RequestBody Player player) {

        //returns a list with all existent players and their username
        List<Player> allPlayers = playerRepository.findAll();
        boolean usernameAlreadyExists = false;

        //checks if the username is null or empty
        if (isNullOrEmpty(player.userName)) {
            return ResponseEntity.badRequest().build(); //if it is, the request is denied
        }

        //checks if the username already exists
        for (int i=0; i<allPlayers.size(); i++){
            if(allPlayers.get(i).userName.equals(player.userName)) usernameAlreadyExists=true; //modifies the value of the boolean to true if that username already exists
        }

        //if the username already exists, the request is denied
        if (usernameAlreadyExists) {
            return ResponseEntity.badRequest().build();
        }

        //otherwise, the player is saved in the database
        Player saved = playerRepository.save(player);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable("id") long id) {

        if (playerRepository.findById(id)==null) return ResponseEntity.badRequest().build();
        else{
            playerRepository.deleteById(id);
        }
        return ResponseEntity.ok(id);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
