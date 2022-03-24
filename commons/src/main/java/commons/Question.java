package commons;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
@JsonSubTypes({
        @Type(value = Question.MultiChoice.class, name = "multiChoice"),
        @Type(value = Question.ChoiceEstimation.class, name = "choiceEstimation"),
        @Type(value = Question.Matching.class, name = "matching"),
        @Type(value = Question.AccurateEstimation.class, name = "accurateEstimation")
})
public abstract class Question {

    /**
     * Question where the player must choose the activity with the highest energyConsumption
     * out of 3 available options.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
    @JsonSubTypes({
            @Type(value = MultiChoice.class),
            @Type(value = ChoiceEstimation.class),
            @Type(value = Matching.class)
    })
    public static abstract class ActivitiesQuestion extends Question {
        protected int correctActivityIndex;
        protected List<Activity> activities;

        public int getCorrectActivityIndex() {
            return correctActivityIndex;
        }

        public List<Activity> getActivities() {
            return activities;
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
    @JsonSubTypes({
            @Type(value = ChoiceEstimation.class, name = "ChoiceEstimation"),
            @Type(value = AccurateEstimation.class, name = "AccurateEstimation")
    })
    public static abstract class EstimationQuestion extends Question {
        protected Activity activity;
    }

    @Getter
    public static class MultiChoice extends ActivitiesQuestion {
        public MultiChoice() {
            super();
        }

        public MultiChoice(List<Activity> activities, int correctActivityIndex) {
            this.activities = activities;
            this.correctActivityIndex = correctActivityIndex;
        }
    }

    /**
     * Question where the player must choose the correct amount of consumption in Wh out of 3
     * different options for a given activity.
     */


    @Getter
    public static class ChoiceEstimation extends EstimationQuestion {
        private List<Integer> consumptionsWh;

        public ChoiceEstimation(Activity activity, List<Integer> consumptionsWh) {
            this.activity = activity;
        }
    }

    /**
     * Question where the player get an activity like "Taking a shower (50l water)" and must
     * select an activity that consumes an equivalent amount of energy out of 3 options.
     */


    @Getter
    public static class Matching extends ActivitiesQuestion {
        private Activity toMatchActivity;

        public Matching(Activity toMatchActivity, List<Activity> activities) {
            this.activities = activities;
        }
    }

    /**
     * Question where the player must enter how much energy an activity consumes in an open
     * field
     */


    @Getter
    public static class AccurateEstimation extends EstimationQuestion {
        public AccurateEstimation(Activity activity) {
            this.activity = activity;
        }
    }


}
