package commons;


import lombok.Getter;

import java.util.List;

public abstract class Question {


  /**
   * Question where the player must choose the activity with the highest energyConsumption
   * out of 3 available options.
   */

  @Getter
  public static class MultiChoice extends Question {
    private final List<Activity> activities;
    private Activity correct;

    public MultiChoice(List<Activity> activities, Activity correct) {
      this.activities = activities;
      this.correct = correct;
    }

    public List<Activity> getActivities() {
      return activities;
    }

    public Activity getCorrect() {
      return correct;
    }
  }
  /**
   * Question where the player must choose the correct amount of consumption in Wh out of 3
   * different options for a given activity.
   */


  @Getter
  public static class ChoiceEstimation extends Question {
    private final List<Activity> activity;

    public ChoiceEstimation(List<Activity> activity, int consumptionWh) {
      this.activity = activity;
    }
  }
  /**
   * Question where the player get an activity like "Taking a shower (50l water)" and must
   * select an activity that consumes an equivalent amount of energy out of 3 options.
   */


  @Getter
  public static class Matching extends Question {
    private final List<Activity> activities;

    public Matching(List<Activity> activities, int consumptionWh) {
      this.activities = activities;
    }
  }
  /**
   * Question where the player must enter how much energy an activity consumes in an open
   * field
   */


  @Getter
  public static class AccurateEstimation extends Question {
    private final List<Activity> activity;

    public AccurateEstimation(List<Activity> activity, int consumptionWh) {
      this.activity = activity;
    }
  }


}
