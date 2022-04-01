package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Activity.ActivityController;
import server.Activity.ActivityService;

import java.util.ArrayList;
import java.util.List;

class ActivityControllerTest {

    private TestActivityRepository repo;
    private ActivityService activityService;
    private ActivityController activityController;

    private List<Activity> activities;

    /**
     * Set up objects needed for the tests
     */
    @BeforeEach
    void initialize() {

        this.activities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Activity activity = new Activity("id" + i, "name" + i, Long.valueOf(i), "source"+ i, null);
            activity.setId("" + i);

            this.activities.add(activity);
        }

        this.repo = new TestActivityRepository();
        this.repo.saveAll(this.activities);
        this.activityService = new ActivityService(this.repo);
        this.activityController = new ActivityController(this.activityService);
    }

    //A random method isn't really testable, so I'm leaving it empty
    @Test
    void getRandomActivity() {
    }
}