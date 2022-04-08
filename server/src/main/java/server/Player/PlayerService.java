package server.Player;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    /**
     * Constructor that sets player repository
     * @param playerRepository
     */
    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository=playerRepository;
    }

    /**
     * Getter for all players
     * @return
     */
    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    /**
     * Method for saving a player
     * @param player
     * @return
     */
    public Player save(Player player) {
        playerRepository.save(player);
        return player;
    }

    /**
     * Method for removing a player from a game
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        List<Player> players = playerRepository.findAll();
        boolean isRemoved = false;
        if (playerRepository.findById(id)!=null) isRemoved = true;

        return isRemoved;
    }
}
