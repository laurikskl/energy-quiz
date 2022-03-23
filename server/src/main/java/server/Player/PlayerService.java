package server.Player;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository=playerRepository;
    }

    /**
     * This is a method equivalent to the GetMapping in the PlayerController - it returns all the player entity from the database.
     * @return a list with  all the players that exist in the database
     */
    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    /**
     * This is a method equivalent to the PostMapping in the PlayerController - it puts a new player in the database.
     * @param player the player that has to be sent over to the database
     * @return the player that was sent
     */
    public Player save(Player player) {
        playerRepository.save(player);
        return player;
    }

    /**
     * This method is equivalent to DeleteMapping in the PlayerController - it deletes a player from the database.
     * @param id the ID of the player to be deleted
     * @return true if the player was deleted from the database
     */
    public boolean delete(Long id) {
        List<Player> players = playerRepository.findAll();
        boolean isRemoved = false;
        if (playerRepository.findById(id)!=null) isRemoved = true;

        return isRemoved;
    }

    public boolean updateScore(Player currentPlayer, int newScore){
        boolean updated = false;
        var listOfPlayers = playerRepository.findAll();
        for (Player player : listOfPlayers){
            if (player.userName.equals(currentPlayer.userName)){
                updated = true;
                player.setScore(newScore);
            }
        }
        return updated;
    }
}
