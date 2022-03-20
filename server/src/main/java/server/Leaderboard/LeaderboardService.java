package server.Leaderboard;

import commons.Player;
import org.springframework.stereotype.Service;
import server.Player.PlayerRepository;

import java.util.Collections;
import java.util.List;

@Service
public class LeaderboardService {

  private  final PlayerRepository repository;

  public LeaderboardService(PlayerRepository repository){
    this.repository = repository;
  }

  public List<Player> getTopPlayers(){
    List<Player> players = repository.findAll();
    Collections.sort(players,(p1, p2) -> p1.score - p2.score);
    List<Player> sortedList = players.subList(0, 14);
    return sortedList;
  }
}
