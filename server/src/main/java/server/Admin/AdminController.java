package server.Admin;

import commons.Activity;
import commons.ActivitySearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Main;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * get all activities
     * @return all activities
     */
    @GetMapping("/getAll")
    public List<Activity> getAll() {
        return adminService.getAll();
    }

    /**
     * @param activitySearchRequest
     * @return list of activities that match given parameters
     */
    @PostMapping("/getByExample")
    public ResponseEntity<List<Activity>> getActivitiesByExample(@RequestBody ActivitySearchRequest activitySearchRequest) {
        return ResponseEntity.ok(adminService.getByExample(activitySearchRequest.getName(), activitySearchRequest.getPowerConsumptionMin(), activitySearchRequest.getPowerConsumptionMax(), activitySearchRequest.getSource(), activitySearchRequest.getImagePath()));
    }

    /**
     * Restart the server
     * @return true if restarting, false otherwise
     */
    @GetMapping("/restart")
    public Boolean restart() {
        return Main.restart();
    }
}
