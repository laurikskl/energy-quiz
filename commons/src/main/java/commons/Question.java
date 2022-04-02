package commons;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Abstract question class containing sub-classes
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = Question.MostNRGQuestion.class, name = "MostNRGQuestion"),
        @Type(value = Question.ChoiceEstimation.class, name = "ChoiceEstimation"),
        @Type(value = Question.Matching.class, name = "Matching"),
        @Type(value = Question.AccurateEstimation.class, name = "AccurateEstimation")
})
public abstract class Question {

    /**
     * activities is the list of activities used in a question
     * correct is the correct activity if applicable
     */

    private List<Activity> activities;
    private List<Long> consumptions;
    private Activity correct;


    /**
     * @param activities the list of activities used in a question
     */

    public Question(List<Activity> activities, List<Long> consumptions) {
        this.activities = activities;
        this.consumptions = consumptions;
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
     * @return the list of consumptions
     */
    public List<Long> getConsumptions() {
        return consumptions;
    }

    /**
     * Set images to null for all the activities in this question
     * Useful when we need to send question with web socket
     */
    public void deleteImages() {
        for (Activity act : this.activities) {
            act.setImageContent(null);
        }
        if (correct != null)
            correct.setImageContent(null);
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
            super(null, null);
            //for object mapper
        }


        /**
         * @param activities the list of activities for this question
         * @param correct    the correct answer (activity with most consumption)
         */

        public MostNRGQuestion(List<Activity> activities, Activity correct, List<Long> consumptions) {
            super(activities, consumptions);
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
         * Zero-parameter constructor
         */

        public ChoiceEstimation() {
            super(null, null);
            //for object mapper
        }


        /**
         * @param activities   the list of activities for this question
         * @param consumptions the list of answers for this question where the correct one is at index 0
         */

        public ChoiceEstimation(List<Activity> activities, List<Long> consumptions) {
            super(activities, consumptions);
        }

    }


    /**
     * Question where the player get an activity like "Taking a shower (50l water)" and must
     * select an activity that consumes an equivalent amount of energy out of 3 options.
     * At index 0 you can find the activity to match to
     */

    @Getter
    @JsonTypeName("Matching")
    public static class Matching extends Question {

        /**
         * Zero-parameter constructor
         */

        public Matching() {
            super(null, null);
            //for object mapper
        }


        /**
         * @param activities the list of activities for this question
         */

        public Matching(List<Activity> activities, List<Long> consumptions) {
            super(activities, consumptions);
            setCorrect(activities.get(1));
            ArrayList<Activity> newList = new ArrayList<>();
            newList.add(activities.get(0));
            ArrayList<Activity> subset = new ArrayList<>();
            for (int i = 1; i < activities.size(); i++) {
                subset.add(activities.get(i));
            }
            Collections.shuffle(subset);
            newList.addAll(subset);
            setActivities(newList);
        }

    }

    /**
     * Question where the player is given a random activity,
     * and they have to guess the consumption of that activity in Wh
     * by entering a valid number
     */

    @Getter
    @JsonTypeName("AccurateEstimation")
    public static class AccurateEstimation extends Question {
        /**
         * Zero-parameter constructor
         */
        public AccurateEstimation() {
            super(null, null);
            //for object mapper
        }

        /**
         * @param activities   - the activity used for this question (at index 0)
         * @param consumptions - the consumption of the activity (also at index 0)
         */
        public AccurateEstimation(List<Activity> activities, List<Long> consumptions) {
            super(activities, consumptions);
        }
    }


}
