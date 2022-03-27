package server.Question;

import commons.Activity;
import commons.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.Activity.ActivityController;
import server.Activity.ActivityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * The service layer for Questions
 * Used to randomly generate questions from activities in the database
 */

@Service
public class QuestionService {

    /**
     * activityRepository is the repository used for our activities
     * We use an instance of random to randomise questions
     */

    private final ActivityRepository activityRepository;
    private final Random random = new Random();
    private final ActivityController activityController;


    /**
     * Constructor where we inject the repo into the service layer
     *
     * @param activityRepository - the repository where we get our activities from
     */

    @Autowired
    public QuestionService(ActivityRepository activityRepository, ActivityController activityController) {
        this.activityRepository = activityRepository;
        this.activityController = activityController;
    }


    /**
     * @return a list of 20 randomised questions
     */

    public List<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        while (questions.size() < 20) {
            questions.add(getRandomQuestion());
        }
        return questions;
    }


    /**
     * @return a question of random type
     */

    public Question getRandomQuestion() {
        int randomType = random.nextInt(2);
        switch (randomType) {
            case 0:
                return getRandomMostNRG();
            case 1:
                return getRandomChoiceEstimation();
            case 2:
                return getRandomMatching();
        }
        return null;
    }


    /**
     * @return a randomised question of type Matching
     */

    public Question.Matching getRandomMatching() {

        List<Activity> activities = new ArrayList<>(); //the list of activities
        Activity activity = activityController.getRandomActivity();

        Activity match = null; //the matching activity (index 1)

        //putting the activity at index 0
        activities.add(activity);

        //finding an activity with around equal consumption by sorting activities and finding the neighbour in the list
        while (match == null) {
            List<Activity> all = activityRepository.findAll();
            Collections.sort(all);

            int index = all.indexOf(activity);
            if (index != activityRepository.count() - 1) {
                match = all.get(index + 1);
            } else {
                match = all.get(index - 1);
            }
        }

        //putting the matching activity at index 1
        activities.add(match);

        //finding other activities as answers
        while (activities.size() < 4) {
            long randomId = random.nextInt((int) activityRepository.count());
            Activity toAdd = null;
            while (toAdd == null) {
                Activity potential = activityController.getRandomActivity();
                if (potential != null && potential.getPowerConsumption() != activity.getPowerConsumption()) {
                    toAdd = potential;
                }
            }
            activities.add(toAdd);
        }

        return new Question.Matching(activities, null);
    }


    /**
     * @return a random question of type ChoiceEstimation
     */

    public Question.ChoiceEstimation getRandomChoiceEstimation() {
        Activity activity = activityController.getRandomActivity();

        //adding the correct answer at index 0
        long correct = activity.getPowerConsumption();
        List<Long> answers = new ArrayList<>();
        answers.add(correct);

        //adding two other answers with a maximum difference of 50%
        int min = (int) Math.floor(0.5 * correct);
        int max = (int) Math.ceil(1.5 * correct);
        while (answers.size() < 3) {
            long randomNumber = random.nextInt(max + 1);
            if (randomNumber >= min && randomNumber != correct) {
                answers.add(randomNumber);
            }
        }

        return new Question.ChoiceEstimation(List.of(activity), answers);
    }


    /**
     * @return a random question of type MostNRGQuestion
     */

    public Question.MostNRGQuestion getRandomMostNRG() {
        List<Activity> activities = new ArrayList<>();
        List<Long> consumptions = new ArrayList<>();
        while (activities.size() < 3) {
            Activity rand = activityController.getRandomActivity();
            Long cons = rand.getPowerConsumption();
            if (!consumptions.contains(cons)) {
                consumptions.add(cons);
                activities.add(rand);
            }
        }
        Collections.sort(activities); //most energy will be in front
        return new Question.MostNRGQuestion(activities, activities.get(0), null);
    }
}
