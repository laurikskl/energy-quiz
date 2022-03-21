package server.Leaderboard;

import commons.Player;
import org.springframework.stereotype.Service;
import server.Player.PlayerRepository;

import java.util.Collections;
import java.util.List;

/**
 * Service layer with all the logic for the leaderboard generating.
 */
@Service
public class LeaderboardService {

  private final PlayerRepository repository;

  public LeaderboardService(PlayerRepository repository) {
    this.repository = repository;
  }

  /**
   * Finds the top 15 players from the respository database using a comparator.
   * @return a list of top 15 players
   */
  public List<Player> getTopPlayers() {
    List<Player> players = repository.findAll();
    Collections.sort(players, (p1, p2) -> p1.score - p2.score);
    List<Player> sortedList = players.subList(0, 14);
    return sortedList;
  }
}
