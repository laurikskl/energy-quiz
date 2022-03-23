package server.Player;

import commons.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * This is a controller for the Player class.
 */

@RestController
@RequestMapping("/player")
public class PlayerController {

    /**
     * playerService is an instance of the playerService
     * playerRepository is an instance of the playerService
     */
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;


    /**
     * This is the constructor for the PlayerController.
     * @param playerService an instance of the playerService
     * @param playerRepository an instance of the playerService
     */
    public PlayerController(PlayerService playerService, PlayerRepository playerRepository) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }


    /**
     * This method returns all players that exist in the database.
     * @return all players in the database
     */
    @GetMapping(path = { "", "/" })
    public List<Player> getAll(){
        return playerService.getPlayers();
    }


    /**
     * This method return a specific player by its ID.
     * @param id the id of the player
     * @return the player with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") long id) {
        if (id<0 || !playerRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(playerRepository.getById(id));
    }


    /**
     * This method sends a player to the database.
     * This method will be used when you update the score of a player - it deletes the current player and stores it as a new entity with the actual score.
     * @param player the player to be added or updated
     * @return the response entity from setting the player
     */
    @PostMapping(path = "/setPlayer")
    public ResponseEntity<Player> setPlayer(@RequestBody Player player) {
        Player test = player;
        //check if player with name already in database, if so delete it
        for(Player toComp : this.getAll()) {
            if(toComp.getUserName().equals(player.getUserName())) {
                playerRepository.deleteById(toComp.id);
            }
        }
        //save the player with the new score to the database
        playerRepository.save(player);
        return ResponseEntity.ok(player);
    }

    /**
     * This method is called to add a new entity of player in the database.
     * It checks if the username of the player is null/empty or if it already exists in the database. If it does, the method returns 400 BAD REQUEST as an answer.
     * Else, it puts the player in the database.
     * @param player the player to add
     * @return the response entity from adding the player
     */
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


    /**
     * This method deletes a player from the database given by the ID.
     * @param id deletes a player by this ID
     * @return response entity of deleting the player
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable("id") long id) {

        if (playerRepository.findById(id)==null) return ResponseEntity.badRequest().build();
        else{
            playerRepository.deleteById(id);
        }
        return ResponseEntity.ok(id);
    }


    /**
     * This method checks if a String is null and is used when posting a player to see if the username is not null/empty.
     * @param s a string
     * @return returns true if s is null or empty
     */

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
