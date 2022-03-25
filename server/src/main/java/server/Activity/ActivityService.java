package server.Activity;

import commons.Activity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ActivityService {

    private final Random random;
    private final ActivityRepository repository;

    /**
     * Constructor that injects random and repository
     * @param repository activity repository
     */
    public ActivityService(ActivityRepository repository) {
        this.random = new Random();
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
