package server.api;

import commons.Activity;
import commons.ActivitySearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.Admin.AdminController;
import server.Admin.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AdminControllerTest {

    private TestActivityRepository repo;
    private AdminService adminService;
    private AdminController adminController;

    private List<Activity> activities;

    /**
     * Set up objects needed for the tests
     */
    @BeforeEach
    void initialize() {

        this.activities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Activity activity = new Activity("name" + i, Long.valueOf(i), "source"+ i, "imagePath" + i);
            activity.setId(Long.valueOf(i));

            this.activities.add(activity);
        }

        this.repo = new TestActivityRepository();
        this.repo.saveAll(this.activities);
        this.adminService = new AdminService(this.repo);
        this.adminController = new AdminController(this.adminService);
    }

    /**
     * Test getAll
     */
    @Test
    void getAll() {
        assertEquals(this.adminController.getAll(), this.activities);
    }

    /**
     * Test getActivitiesByExample
     */
    @Test
    void getActivitiesByExample() {
        ActivitySearchRequest activitySearchRequest = new ActivitySearchRequest("name0", 0l, 0l, "source0", "imagePath0");

        assertEquals(this.adminController.getActivitiesByExample(activitySearchRequest), ResponseEntity.ok(List.of(this.activities.get(0))));
    }

    /**
     * Test removeById
     */
    @Test
    void removeById() {
        this.adminController.removeById(0l);
        List<Activity> activities = this.adminController.getAll();

        assertEquals(activities.indexOf(this.activities.get(0)), -1);
    }
}