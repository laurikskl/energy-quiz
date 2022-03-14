package server.api;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

import java.util.Random;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final Random random;
    private final ActivityRepository repository;

    /**
     * Constructor that injects random and repository
     *
     * @param random
     * @param repository
     */
    public ActivityController(Random random, ActivityRepository repository) {
        this.random = random;
        this.repository = repository;
    }

    /**
     * Return random activity
     *
     * @return
     */
    @GetMapping(path = {"rnd"})
    public ResponseEntity<Activity> getRandomActivity() {
        //Get a random number that is smaller than number of elements in repository
        var randomId = random.nextInt((int) repository.count());
        //Return random activity
        return ResponseEntity.ok(repository.getById((long) randomId));
    }
}
