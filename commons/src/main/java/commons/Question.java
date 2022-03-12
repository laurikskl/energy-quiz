package commons;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@Type(value = Question.MultiChoice.class, name = "mc"),
    @Type(value = Question.ChoiceEstimation.class, name = "choiceEstimation"),
    @Type(value = Question.Matching.class, name = "matching"),
    @Type(value = Question.AccurateEstimation.class, name = "accurateEstimation"),

})
public abstract class Question {

  /**
   * Question where the player must choose the activity with the highest energyConsumption
   * out of 3 available options.
   */

  @AllArgsConstructor
  @Getter
  public static class MultiChoice extends Question {
    private final List<Activity> activities;
    private final int consumptionWh;

  }
  /**
   * Question where the player must choose the correct amount of consumption in Wh out of 3
   * different options for a given activity.
   */

  @AllArgsConstructor
  @Getter
  public static class ChoiceEstimation extends Question {
    private final Activity activity;
    private final int consumptionWh;
  }
  /**
   * Question where the player get an activity like "Taking a shower (50l water)" and must
   * select an activity that consumes an equivalent amount of energy out of 3 options.
   */

  @AllArgsConstructor
  @Getter
  public static class Matching extends Question {
    private final List<Activity> activities;
    private final int consumptionWh;

  }
  /**
   * Question with x possible activities to choose, one of them is the correct answer
   */

  @AllArgsConstructor
  @Getter
  public static class AccurateEstimation extends Question {
    private final Activity activity;
    private final int consumptionWh;
  }


}
