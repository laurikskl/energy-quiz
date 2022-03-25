package commons;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.List;


/**
 * Abstract question class containing sub-classes
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = Question.MostNRGQuestion.class, name = "MostNRGQuestion"),
        @Type(value = Question.ChoiceEstimation.class, name = "ChoiceEstimation"),
        @Type(value = Question.Matching.class, name = "Matching"),
})
public abstract class Question {

    /**
     * activities is the list of activities used in a question
     * correct is the correct activity if applicable
     */

    private List<Activity> activities;
    private Activity correct;


    /**
     * @param activities the list of activities used in a question
     */

    public Question(List<Activity> activities) {
        this.activities = activities;
    }


    /**
     * @return the correct Activity
     */

    public Activity getCorrect() {
        return correct;
    }

    /**
     * @param correct the correct Activity
     */

    public void setCorrect(Activity correct) {
        this.correct = correct;
    }

    /**
     * @return the list of activities
     */

    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * @param activities the list of activities
     */

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * Question where the player must choose the activity with the highest energyConsumption
     * out of 3 available options.
     */
    @Getter
    @JsonTypeName("MostNRGQuestion")
    public static class MostNRGQuestion extends Question {

        /**
         * Zero-parameter constructor
         */

        public MostNRGQuestion() {
            super(null);
            //for object mapper
        }


        /**
         * @param activities the list of activities for this question
         * @param correct    the correct answer (activity with most consumption)
         */

        public MostNRGQuestion(List<Activity> activities, Activity correct) {
            super(activities);
            setCorrect(correct);
        }
    }


    /**
     * Question where the player must choose the correct amount of consumption in Wh out of 3
     * different options for a given activity.
     */

    @Getter
    @JsonTypeName("ChoiceEstimation")
    public static class ChoiceEstimation extends Question {

        /**
         * The list of answers where the correct answer is at index 0
         */

        private List<Long> consumptions;


        /**
         * Zero-parameter constructor
         */

        public ChoiceEstimation() {
            super(null);
            //for object mapper
        }


        /**
         * @param activities   the list of activities for this question
         * @param consumptions the list of answers for this questions where the correct one is at index 0
         */

        public ChoiceEstimation(List<Activity> activities, List<Long> consumptions) {
            super(activities);
            this.consumptions = consumptions;
        }


        /**
         * @return the list of consumptions with the correct answer at index 0
         */

        public List<Long> getConsumptions() {
            return consumptions;
        }

    }


    /**
     * Question where the player get an activity like "Taking a shower (50l water)" and must
     * select an activity that consumes an equivalent amount of energy out of 3 options.
     * At index 0 is the activity to compare to
     * At index 1 is the matching activity
     */

    @Getter
    @JsonTypeName("Matching")
    public static class Matching extends Question {

        /**
         * Zero-parameter constructor
         */

        public Matching() {
            super(null);
            //for object mapper
        }


        /**
         * @param activities the list of activities for this question
         */

        public Matching(List<Activity> activities) {
            super(activities);
            setCorrect(activities.get(1));
        }

    }


}
