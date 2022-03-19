package server.MultiChoice;

import commons.Activity;
import org.springframework.stereotype.Service;
import server.Activity.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MultiChoiceService {

  private final ActivityRepository repository;
  private final Random random;

  public MultiChoiceService(Random random, ActivityRepository repository) {
    this.random = random;
    this.repository = repository;
  }

  /**
   *
   * @param  activities a list of 3 activites
   * @return the correct answer AKA the one with the largest consumption in Wh
   */
  public Activity findCorrect(List<Activity> activities) {
    if(activities == null || activities.size() == 0) return null;
    Activity max = activities.get(0);
    for(Activity activity : activities) {
      if(activity.getPowerConsumption() > max.getPowerConsumption()) {
        max = activity;
      }
    }
    return max;
  }

  /**
   * this is for generating the questions
   * @return a list of 3 activities
   */
  public List<Activity> getList(){
    List<Activity> activities = new ArrayList<>();
    while(activities.size() < 3) {
      Activity toAdd = getRandomActivity();
      if(!activities.contains(toAdd)) activities.add(toAdd);
    }
    return activities;
  }

  /**
   * I used the activity repository for this one and im not sure if that is correct
   * but I could not get around circular dependency issues otherwise, I did not want
   * to call another service or controller from this one
   * @return A random activity from the activity bank
   */
  public Activity getRandomActivity() {
    //Get a random number that is smaller than number of elements in repository
    var randomId = random.nextInt((int) repository.count());
    //Return random activity
    return repository.getById((long) randomId);
  }
}
