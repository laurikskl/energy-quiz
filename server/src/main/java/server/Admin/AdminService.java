package server.Admin;

import commons.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

@Service
public class AdminService {

    private final ActivityRepository repository;

    /**
     * Constructor that injects random and repository
     * @param repository activity repository
     */
    @Autowired
    public AdminService(ActivityRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all Activities
     */
    public List<Activity> getAll() {
        return repository.findAll();
    }


    /**
     * Select and return all Activities that meet the criteria from the database
     * @param name if this is a substring of the property path "name", select that activity
     * @param powerConsumptionMin minimum of the powerConsumption range
     * @param powerConsumptionMax maximum of the powerConsumption range
     * @param source if this is a substring of the property path "source", select that activity
     * @return a list of selected Activities
     */
    public List<Activity> getByExample(String name, Long powerConsumptionMin, Long powerConsumptionMax, String source) {

        Activity activity = new Activity(name, null, source, null);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("source", GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("imagePath", GenericPropertyMatchers.contains().ignoreCase())
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

    /**
     * Remove activity by ID
     * @param ID
     * @return true if removing, false otherwise
     */
    public Boolean removeById(Long ID) {
        Activity activity = new Activity();
        activity.setId(ID);

        ExampleMatcher matcher = ExampleMatcher
                .matching().withIgnoreNullValues();

        Example<Activity> activityExample = Example.of(activity, matcher);

        List<Activity> activities = this.repository.findAll(activityExample);

        if (activities.size() < 1) {
            return false;
        }

        this.repository.delete(activities.get(0));
        return true;
    }

}
