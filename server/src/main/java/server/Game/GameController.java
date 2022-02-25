package server.Game;


import commons.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/game")
public class GameController {

    private final Random random;
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }
    @GetMapping
    public List<Game> getGames() {
        return gameService.getGames();
    }

    @GetMapping("rnd")
    public Game getRandom(){
        var idx = random.nextInt((int) gameService.count());
        return ResponseEntity.ok(gameService.getGameById((long) idx));
    }

}
