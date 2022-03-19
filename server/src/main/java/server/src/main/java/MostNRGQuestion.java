package server.src.main.java;

import commons.Activity;
import commons.Question;
import server.Activity.ActivityController;

//import java.util.ArrayList;
import java.util.List;
//import java.util.Objects;

/**
 * Question type where you're asked which of three activities consumes the most energy
 * Some commented out code will be re-added once the abstract question class has been made
 */

public class MostNRGQuestion extends Question {

    private Activity correct;
    private List<Activity> activities;
    private ActivityController activityController;

    /**
     * constructor
     * The list of activities is filled with three unique activities
     * The correct answer is the activity with the most energy consumption
     */
//    MostNRGQuestion(ActivityController activityController) {
//        this.activityController = activityController;
//        this.activities = new ArrayList<>();
//        while(activities.size() < 3) {
//            Activity toAdd = activityController.getRandomActivity().getBody();
//            if(!activities.contains(toAdd)) activities.add(toAdd);
//        }
//        this.correct = findMax();
//    }

    /**
     * constructor with activities parameter
     * The correct answer is the activity with the most energy consumption
     * This constructor is used in tests
     */
//    MostNRGQuestion(List<Activity> activities) {
//        this.activities = activities;
//        this.correct = findMax();
//    }

    /**
     * @return the activity with the most energy consumption
     * or null if there are no activities
     */
//    public Activity findMax() {
//        if(activities == null || activities.size() == 0) return null;
//        Activity max = activities.get(0);
//        for(Activity activity : activities) {
//            if(activity.getPowerConsumption() > max.getPowerConsumption()) {
//                max = activity;
//            }
//        }
//        return max;
//    }

    /**
     * @return the correct answer Activity
     */


}
