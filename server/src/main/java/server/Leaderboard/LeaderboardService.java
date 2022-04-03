package server.Leaderboard;

import commons.Player;
import org.springframework.stereotype.Service;
import server.database.PlayerRepository;

import java.util.Collections;
import java.util.List;

@Service
public class LeaderboardService {

  private  final PlayerRepository repository;

  public LeaderboardService(PlayerRepository repository){
    this.repository = repository;
  }

  /**
   * Fetches all the players existent in the Player database and creates a list of 15 entities, ordered by the score of the players.
   * @return
   */
  public List<Player> getTopPlayers(){
    List<Player> players = repository.findAll();
    Collections.sort(players,(p1, p2) -> p1.score - p2.score);
    List<Player> sortedList = players.subList(0, 14);
    return sortedList;

  }
}
