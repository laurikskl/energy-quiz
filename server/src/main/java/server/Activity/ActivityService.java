package server.Activity;

import commons.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class ActivityService {

    private final Random random;
    private final ActivityRepository repository;

    /**
     * Constructor that injects random and repository
     *
     * @param repository activity repository
     */
    @Autowired
    public ActivityService(ActivityRepository repository) {
        this.random = new Random();
        this.repository = repository;
    }

    /**
     * Return random activity
     *
     * @return random Activity
     */

    public Activity getRandomActivity() {
        int randomId = random.nextInt((int) repository.count());
        Optional<Activity> activity = repository.findById((long) randomId);

        return activity.get();
    }

}
