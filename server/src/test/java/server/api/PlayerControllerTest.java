package server.api;

import commons.Player;
import server.Player.PlayerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Player.PlayerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class PlayerControllerTest {

    public int nextInt;
    private PlayerService playerService;
    private TestPlayerRepository repo;

    private PlayerController sut;

    @BeforeEach
    public void setup() {
        repo = new TestPlayerRepository();
        sut = new PlayerController(playerService, repo);
    }

    @Test
    public void setPlayerTest1() {
        var actual = sut.add(new Player(null, 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void setPlayerTest2() {
        var addFirstPlayer = sut.add(new Player("Adda", 372));
        var actual = sut.add(new Player("Adda", 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void setPlayerTest3() {
        var actual = sut.add(new Player("Adda", 0));
        assertEquals(OK, actual.getStatusCode());
    }
}

    /*
    @Test
    public void getAllPlayers(){
        sut.add(new Player("Adda", 0));
        sut.add(new Player("MaxWhoIsSendingMessagesRightNowOnDiscord", 999));
        var actual = sut.getAll();
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add((new Player("Adda", 0)));
        allPlayers.add((new Player("MaxWhoIsSendingMessagesRightNowOnDiscord", 999)));
        assertEquals(allPlayers,actual);
    }
    */
