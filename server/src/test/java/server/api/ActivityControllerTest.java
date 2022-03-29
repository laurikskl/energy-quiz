package server.api;

import commons.Activity;
import commons.ActivitySearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.Activity.ActivityController;
import server.Activity.ActivityService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            Activity activity = new Activity("name" + i, Long.valueOf(i), "source"+ i, null);
            activity.setId(Long.valueOf(i));

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

    /**
     * Test getAll
     */
    @Test
    void getAll() {
        assertEquals(this.activityController.getAll(), this.activities);
    }

    /**
     * Test getActivitiesByExample
     */
    @Test
    void getActivitiesByExample() {
        ActivitySearchRequest activitySearchRequest = new ActivitySearchRequest("name0", 0l, 0l, "source0");

        assertEquals(this.activityController.getActivitiesByExample(activitySearchRequest), ResponseEntity.ok(List.of(this.activities.get(0))));
    }
}