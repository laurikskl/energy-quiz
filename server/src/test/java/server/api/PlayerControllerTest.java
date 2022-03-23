package server.api;

import commons.Player;
import server.Player.PlayerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Player.PlayerService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PlayerControllerTest {

    public int nextInt;
    private PlayerService playerService;
    private TestPlayerRepository repo;

    private PlayerController sut;

    @BeforeEach
    public void setup() {
        repo = new TestPlayerRepository();
        playerService = new PlayerService(repo);
        sut = new PlayerController(playerService, repo);
    }

    @Test
    public void constructor() {
        assertNotNull(sut);
    }


    @Test
    public void cannotAddNullPlayerWithNullUsername() {
        var actual = sut.add(new Player(null, 0));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
}