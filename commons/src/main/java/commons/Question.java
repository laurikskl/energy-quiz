package commons;


import lombok.Getter;

import java.util.List;

public abstract class Question {

    /**
     * Question where the player must choose the activity with the highest energyConsumption
     * out of 3 available options.
     */

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

    public static abstract class EstimationQuestion extends Question {
        protected Activity activity;
    }

    @Getter
    public static class MultiChoice extends ActivitiesQuestion {
        public MultiChoice(List<Activity> activities, int correctActivityId) {
            this.activities = activities;
            this.correctActivityIndex = correctActivityId;
        }

        public int getCorrectActivityId() {
            return correctActivityIndex;
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
