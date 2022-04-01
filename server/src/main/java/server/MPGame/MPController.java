package server.MPGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the MPGames
 */
@RestController
@RequestMapping("api/lobby")
public class MPController {

    private final MPService mpService;

    @Autowired
    public MPController(MPService mpService){
        this.mpService = mpService;
    }
}
