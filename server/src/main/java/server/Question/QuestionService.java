package server.Question;

import commons.Activity;
import commons.Question;
import org.springframework.stereotype.Service;
import server.Activity.ActivityRepository;

import java.util.*;


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


  /**
   * Constructor where we inject the repo into the service layer
   * @param activityRepository - the repository where we get our activities from
   */

  public QuestionService(ActivityRepository activityRepository) {
    this.activityRepository = activityRepository;
  }


  /**
   * @return a list of 20 randomised questions
   */

  public List<Question> getQuestions() {
    ArrayList<Question> questions = new ArrayList<>();
    while(questions.size() < 20) {
      questions.add(getRandomQuestion());
    }
    return questions;
  }


  /**
   * @return a question of random type
   */

  public Question getRandomQuestion() {
    int randomType = random.nextInt(3);
    switch(randomType) {
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
    Activity activity = null; //the activity to match (index 0)
    Activity match = null; //the matching activity (index 1)

    //getting an activity
    while(activity == null) {
      long randomId = random.nextInt((int) activityRepository.count());
      Optional<Activity> activityOptional = activityRepository.findById(randomId);
      if(activityOptional.isPresent()) {
        activity = activityOptional.get();
      }
    }

    //putting the activity at index 0
    activities.add(activity);

    //finding an activity with around equal consumption by sorting it and finding the neighbour in the list
    while(match == null) {
      List<Activity> all = activityRepository.findAll();
      Collections.sort(all);

      int index = all.indexOf(activity);
      if(index != activityRepository.count() - 1) {
        match = all.get(index + 1);
      } else {
        match = all.get(index - 1);
      }
    }

    //putting the matching activity at index 1
    activities.add(match);

    //finding other activities as answers
    while(activities.size() < 4) {
      long randomId = random.nextInt((int) activityRepository.count());
      Activity toAdd = null;
      while(toAdd == null) {
        Activity potential = getRandomActivity();
        if(potential != null && potential.getPowerConsumption() != activity.getPowerConsumption()) {
          toAdd = potential;
        }
      }
      activities.add(toAdd);
    }

    return new Question.Matching(activities);
  }


  /**
   * @return a random question of type ChoiceEstimation
   */

  public Question.ChoiceEstimation getRandomChoiceEstimation() {
    Activity activity = getRandomActivity(); //the activity to estimate the consumption of

    //adding the correct answer at index 0
    long correct = activity.getPowerConsumption();
    List<Long> answers = new ArrayList<>();
    answers.add(correct);

    //adding two other answers with a maximum difference of 50%
    int min = (int) Math.floor(0.5 * correct);
    int max = (int) Math.ceil(1.5 * correct);
    while(answers.size() < 3) {
      long randomNumber = random.nextInt(max + 1);
      if(randomNumber >= min && randomNumber != correct) {
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
    int max = (int) activityRepository.count();
    List<Integer> activityIds = new ArrayList<>();
    Activity correct = null;
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
        if (activity.getPowerConsumption() > maxConsumption){
          maxConsumption = activity.getPowerConsumption();
          correct = activity;
        }

        //If the activity has the same PowerConsumption as the max, it gets discarded from the list,
        //as that would mean two correct answers
        else if (activity.getPowerConsumption() == maxConsumption){
          continue;
        }

        activities.add(activity);
        activityIds.add(randomId);
      }
    }

    return new Question.MostNRGQuestion(activities, correct);
  }


  /**
   * @return a random activity from the database
   */

  public Activity getRandomActivity() {
    Activity activity = null;
    while(activity == null) {
      long randomId = random.nextInt((int) activityRepository.count());
      Optional<Activity> activityOptional = activityRepository.findById(randomId);
      if(activityOptional.isPresent()) {
        activity = activityOptional.get();
      }
    }
    return activity;
  }

}
