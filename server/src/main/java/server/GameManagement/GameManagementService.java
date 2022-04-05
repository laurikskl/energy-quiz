package server.GameManagement;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import server.Question.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Logic behind the methods needed for gameController.
 */

@Service
@Component
public class GameManagementService {

    public final QuestionService questionService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    public List<Game> games;
    public int generateId;

    /**
     * Constructor for the gameService class that starts a new arrayList with games
     * and for this launch of the server, starts to count IDs for the games up
     * from 0.
     */
    @Autowired
    public GameManagementService(QuestionService questionService, SimpMessagingTemplate simpMessagingTemplate) {
        this.questionService = questionService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.games = new ArrayList<>();
        this.generateId = 0;
    }

    /**
     * fetches a game by its id.
     *
     * @param id the id of the game were looking for
     * @return returns the game with the id or if no such game exists, a new one
     */

    public Game getById(long id) {
        Game game = null;

        for (Game current : games) {
            if (current.getId() == id) {
                game = current;
                break;
            }
        }

        if (game == null) {
            throw new IllegalStateException();
        }

        return game;
    }

    /**
     * Creates a new game with a unique id.
     *
     * @return fresh game with unique id and empty players and questions lists
     */

    public Game newLobby() {
        List<Player> players = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        Game newGame = new Game(generateId, players, questions);
        generateId++;
        return newGame;
    }

    /**
     * Adds the lobby to the list of games.
     *
     * @param lobbyToAdd The lobby that just got started
     */

    public void makeLobbyActive(Game lobbyToAdd) {
        games.add(lobbyToAdd);
    }

    /**
     * gets the id of the next game that will be created.
     *
     * @return current id
     */

    public long getGenerateId() {
        return generateId;
    }

    /**
     * Get the list of all active games.
     *
     * @return all active games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Update the score of the player that sent their new score
     *
     * @param id          the id of the game the player is in
     * @param roundPlayer the player that is updating their score
     */
    public void scoreUpdate(long id, RoundPlayer roundPlayer) {
        Game game = this.getById((int) id);
        // the amount of points the player wants to add
        int score = roundPlayer.getScore();

        // if the round the player is sending the score from doesn't match
        // the current round of the game then we don't give the player
        // points
        if (game.getRound() != roundPlayer.getRound()) return;

        // we look through the players in the game to find the one
        // that wants to update their score
        for (Player player : game.getPlayers()) {
            if (player.getUserName().equals(roundPlayer.getUserName())) {
                //the score of the player before adding to it
                int currentScore = player.getScore();
                player.setScore((score + currentScore));
            }
        }
    }

    /**
     * Method for starting the game
     *
     * @param id
     */
    public void startGame(long id) throws InterruptedException {
        //Get the game object of the correct id
        Game game = this.getById(id);
        System.out.println("Game id = " + game.getId());

        //Get the list of 20 questions
        List<Question> questionList = questionService.getQuestions();

        //Set the correct screen for the MP game
        game.type = Type.STARTMP;
        game.screen = Screen.QUESTION;

        System.out.println("Sending game object back to client, id = " + id);
        simpMessagingTemplate.convertAndSend("/topic/game/" + id, game);
        System.out.println(game.getPlayers().toString());

        //Start the questions
        for (Question question : questionList) {
            //Send Questions
            game.type = Type.QUESTION;
            game.screen = Screen.QUESTION;

            game.setRound(game.getRound() + 1);

            question.deleteImages();
            game.setQuestion(question);

            System.out.println("Sending game object back to client, id = " + id);
            simpMessagingTemplate.convertAndSend("/topic/game/" + id, game);

            sleep(15500);

            //Send Scoreboard
            game.screen = Screen.SCOREBOARD;
            game.setPlayers(game.getPlayers());
            System.out.println("Sending game object back to client, id = " + id);
            simpMessagingTemplate.convertAndSend("/topic/game/" + id, game);

            sleep(3000);
        }
        //Go over the questions and send them one by one with a (15.5) seconds break
        //Before sending the question update the scores of the players and wait 3 seconds

    }


}
