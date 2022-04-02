package server.Admin;

import commons.Activity;
import commons.ActivityBank;
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
     * Method for clients to check if the server is running.
     * @return If the server is running. Always true
     */
    @GetMapping("/running")
    public Boolean running() {
        return true;
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
        return ResponseEntity.ok(adminService.getByExample(activitySearchRequest.getId(), activitySearchRequest.getName(), activitySearchRequest.getPowerConsumptionMin(), activitySearchRequest.getPowerConsumptionMax(), activitySearchRequest.getSource()));
    }

    /**
     * Add activities from an Activity Bank with the choice to retain the old Activities or to override them.
     * @param activityBank Class containing a list of activities to add and a boolean if the previous activities should be deleted
     * @return the amount of Activities added
     */
    @PostMapping("/addBank")
    public Integer addBank(@RequestBody ActivityBank activityBank) {
        return adminService.AddBank(activityBank.getActivities(), activityBank.getOverride());
    }

    /**
     * Remove activity by ID
     * @param ID
     * @return true if removing, false otherwise
     */
    @PostMapping("/removeById")
    public Boolean removeById(@RequestBody String ID) {
        return this.adminService.removeById(ID);
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
