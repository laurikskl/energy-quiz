package server.Question;

import commons.Activity;
import commons.Question;
import org.springframework.stereotype.Service;
import server.Activity.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionService {

    private ActivityRepository activityRepository;
    private Random random = new Random();

    /**
     * Constructor where we inject the repo into the service layer
     *
     * @param activityRepository - the repository where we get our activities from
     */
    public QuestionService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Basic method for returning a random question of a random type
     */
    public Question getRandomQuestion() {
        // TODO get random type of question (1 from 4), for now only Multichoice
    /*
    int randomType = random.nextInt(4);
    switch(randomType){
      case 0: return getRandomMultiChoice();
      case 1: return getRandomChoiceEstimation();
      case 2: return getRandomMatching();
      case 3: return getRandomAccurateEstimation();
      break;
      */
        return getRandomMultiChoice();
    }

    /**
     * Method for returning a random question of type MultiQuestion
     */
    public Question getRandomMultiChoice() {
        List<Activity> activities = new ArrayList<>();
        int max = (int) activityRepository.count();
        List<Integer> activityIds = new ArrayList<>();
        int correctActivityId = -1;
        long maxConsumption = 0;

        // Adding 3 random activities to a list, while also checking that they are different
        while (activityIds.size() < 3) {
            Activity activity;
            int randomId = random.nextInt(max);
            if (!activityIds.contains(randomId)) {
                Optional<Activity> optionalActivity = activityRepository.findById((long) randomId);
                if (optionalActivity.isEmpty()) {
                    continue;
                }
                activity = optionalActivity.get();
                //Determining the maximum PowerConsumption of the three activities, and therefore the correct answer
                if (activity.getPowerConsumption() > maxConsumption) {
                    maxConsumption = activity.getPowerConsumption();
                    correctActivityId = randomId;
                }

                //If the activity has the same PowerConsumption as the max, it gets discarded from the list,
                //as that would mean two correct answers
                else if (activity.getPowerConsumption() == maxConsumption) {
                    continue;
                }

                activities.add(activity);
                activityIds.add(randomId);
            }
        }

        Question.MultiChoice question = new Question.MultiChoice(activities, correctActivityId);
        return question;
    }
}
