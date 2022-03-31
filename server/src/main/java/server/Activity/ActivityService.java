package server.Activity;

import commons.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

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
        Activity activity = null;
        while (activity == null) {
            long randomId = random.nextInt((int) repository.count());
            Optional<Activity> activityOptional = repository.findById(randomId);
            if (activityOptional.isPresent()) {
                activity = activityOptional.get();
            }
        }
        return activity;
    }

    /**
     * @return all Activities
     */
    public List<Activity> getAll() {
        return repository.findAll();
    }


    /**
     * Select and return all Activities that meet the criteria from the database
     *
     * @param name                if this is a substring of the property path "name", select that activity
     * @param powerConsumptionMin minimum of the powerConsumption range
     * @param powerConsumptionMax maximum of the powerConsumption range
     * @param source              if this is a substring of the property path "source", select that activity
     * @param imageContent        if this is a substring of the property path "imagePath", select that activity
     * @return a list of selected Activities
     */
    public List<Activity> getByExample(String name, Long powerConsumptionMin, Long powerConsumptionMax, String source) {

        Activity activity = new Activity(name, null, source, null);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("source", GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("imageContent", GenericPropertyMatchers.contains().ignoreCase())
                .withIgnoreNullValues();

        Example<Activity> activityExample = Example.of(activity, matcher);

        List<Activity> activities = repository.findAll(activityExample);

        if (powerConsumptionMin == null) powerConsumptionMin = Long.MIN_VALUE;
        if (powerConsumptionMax == null) powerConsumptionMax = Long.MAX_VALUE;

        //using final Longs for the filter
        final Long finalPowerConsumptionMin = powerConsumptionMin;
        final Long finalPowerConsumptionMax = powerConsumptionMax;

        //filtering for powerConsumption range, because ExampleMatcher doesn't support range queries.
        activities = activities.stream().filter(activity1 -> (activity1.getPowerConsumption() >= finalPowerConsumptionMin) && (activity1.getPowerConsumption() <= finalPowerConsumptionMax)).collect(Collectors.toList());

        return activities;
    }
}
