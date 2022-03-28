package server.Activity;

import commons.Activity;
import commons.ActivitySearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Return random activity
     *
     * @return A random activity
     */
    @GetMapping("/getRandomActivity")
    public Activity getRandomActivity() {
        return activityService.getRandomActivity();
    }

}
