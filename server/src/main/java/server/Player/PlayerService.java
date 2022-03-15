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

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public Player save(Player player) {
        playerRepository.save(player);
        return player;
    }

    public boolean delete(Long id) {
        List<Player> players = playerRepository.findAll();
        boolean isRemoved = false;
        if (playerRepository.findById(id)!=null) isRemoved = true;

        return isRemoved;
    }
}