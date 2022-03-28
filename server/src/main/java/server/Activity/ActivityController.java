package server.Activity;

import commons.Activity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
