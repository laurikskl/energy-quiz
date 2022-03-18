package server.MultiChoice;

import commons.Activity;
import org.springframework.stereotype.Service;
import server.api.ActivityController;

import java.util.List;

@Service
public class MultiChoiceService {

  private Activity correct;
  private List<Activity> activities;
  private ActivityController activityController;

  public Activity findMax() {
    if(activities == null || activities.size() == 0) return null;
    Activity max = activities.get(0);
    for(Activity activity : activities) {
      if(activity.getPowerConsumption() > max.getPowerConsumption()) {
        max = activity;
      }
    }
    return max;
  }
}
