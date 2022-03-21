package server.Activity;

import commons.Activity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Service layer for activities where the logic is.
 */

@RestController
@RequestMapping("/api/activities")
public class ActivityService {

  private final Random random;
  private final ActivityRepository repository;

  /**
   * Constructor that injects random and repository.
   *
   * @param random Random class
   * @param repository The activity repository
   */
  public ActivityService(Random random, ActivityRepository repository) {
    this.random = random;
    this.repository = repository;
  }

  /**
   * Generates a random activity from the database.
   *
   * @return returns a random activity.
   */

  public Activity getRandomActivity() {
    //Get a random number that is smaller than number of elements in repository
    var randomId = random.nextInt((int) repository.count());
    //Return random activity
    return repository.getById((long) randomId);
  }
}
