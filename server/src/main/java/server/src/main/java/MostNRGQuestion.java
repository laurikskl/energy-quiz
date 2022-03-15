package server.src.main.java;

import commons.Activity;
import commons.Question;
import server.api.ActivityController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    MostNRGQuestion(ActivityController activityController) {
        this.activityController = activityController;
        this.activities = new ArrayList<>();
        while(activities.size() < 3) {
            Activity toAdd = activityController.getRandomActivity().getBody();
            if(!activities.contains(toAdd)) activities.add(toAdd);
        }
        this.correct = findMax();
    }

    /**
     * constructor with activities parameter
     * The correct answer is the activity with the most energy consumption
     * This constructor is used in tests
     */
    MostNRGQuestion(List<Activity> activities) {
        this.activities = activities;
        this.correct = findMax();
    }

    /**
     * @return the activity with the most energy consumption
     * or null if there are no activities
     */
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

    /**
     * @return the correct answer Activity
     */
    public Activity getCorrect() {
        return correct;
    }

    /**
     * @return the list of activities involved in this question
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * @param correct the correct answer to this question
     */
    public void setCorrect(Activity correct) {
        this.correct = correct;
    }

    /**
     * @param activities the list of activities involved in this question
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * @param o an object
     * @return true iff the object o is also an instance of MostNRGQuestion
     * and has the same values for each attribute
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MostNRGQuestion that = (MostNRGQuestion) o;
        return Objects.equals(correct, that.correct) && Objects.equals(activities, that.activities);
    }

    /**
     * @return string representation of an object of this class
     */
    @Override
    public String toString() {
        return "MostNRGQuestion{" +
                "correct=" + correct +
                ", activities=" + activities +
                '}';
    }
}
