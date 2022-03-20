package server.Leaderboard;

import commons.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class LeaderboardController {

  private final LeaderboardService leaderboardService;

  public LeaderboardController(LeaderboardService leaderboardService){
    this.leaderboardService = leaderboardService;
  }

  @GetMapping("/leaderboard")
  public List<Player> getLeaderboard(){
    List<Player> top15players = leaderboardService.getTopPlayers();
    return top15players;
  }
}
