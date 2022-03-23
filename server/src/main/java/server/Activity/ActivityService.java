package server.Activity;

import commons.Activity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/activities")
public class ActivityService {

    private final Random random;
    private final ActivityRepository repository;

    /**
     * Constructor that injects random and repository
     *
     * @param random random instance
     * @param repository activity repository
     */
    public ActivityService(Random random, ActivityRepository repository) {
        this.random = random;
        this.repository = repository;
    }

    /**
     * Return random activity
     *
     * @return a random activity
     */

    public Activity getRandomActivity() {
        Activity activity = null;
        while(activity == null) {
            long randomId = random.nextInt((int) repository.count());
            Optional<Activity> activityOptional = repository.findById(randomId);
            if(activityOptional.isPresent()) {
                activity = activityOptional.get();
            }
        }
        return activity;
    }
}
