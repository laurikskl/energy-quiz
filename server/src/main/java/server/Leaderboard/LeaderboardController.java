package server.Leaderboard;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.PlayerRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

  @Autowired
  private final PlayerRepository repository;

  public LeaderboardController(PlayerRepository repository){
    this.repository = repository;
  }

  /**
   * @return the 15 players with the highest score.
   */
  @GetMapping(path = {"", "/"})
  public List<Player> getTopLeaderboard() {
    List<Player> players = repository.findAll();
    return players
            .stream()
            .sorted(Comparator.comparing(Player::getScore).reversed())
            .limit(15)
            .collect(Collectors.toList());
  }

}
