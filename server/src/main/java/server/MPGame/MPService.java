package server.MPGame;

import commons.Game;
import commons.Player;
import commons.Question;
import commons.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import server.GameManagement.GameManagementService;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Logic for MPGames
 */

@Service
@Component
public class MPService {

    //Fields
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameManagementService gameManagementService;

    @Autowired
    public MPService(GameManagementService gameManagementService,
                        SimpMessagingTemplate simpMessagingTemplate) {
        this.gameManagementService = gameManagementService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Run the serverside multiplayer game
     */
    synchronized public void run(int id) throws InterruptedException {
        Game game = gameManagementService.getById(id);
        List<Question> questions = game.getQuestions();

        Game gameRound = new Game();

        for (int i = 0; i < 20; i++) {
            game.setRound(i+1);
            gameRound.setRound(i+1);

            gameRound.getQuestions().set(0, questions.get(i));
            gameRound.setPlayers(game.getPlayers());

            simpMessagingTemplate.convertAndSend("/topic/game/" + id, gameRound);

            sleep(15500);

            gameRound.screen = Screen.SCOREBOARD;
            gameRound.setPlayers(game.getPlayers());

            simpMessagingTemplate.convertAndSend("/topic/game/" + id, gameRound);

            sleep(3000);

        }
    }
}
