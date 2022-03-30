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
import java.util.concurrent.ThreadLocalRandom;


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
            Question q = getRandomQuestion();
            if(q != null) {
                questions.add(q);
            }
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
            Activity toAdd = null;
            while (toAdd == null) {
                Activity potential = activityController.getRandomActivity();
                if (potential != null && !potential.getPowerConsumption().equals(activity.getPowerConsumption())) {
                    toAdd = potential;
                }
            }
            activities.add(toAdd);
        }

        return new Question.Matching(activities, null);
    }


    /**
     * We make the answer generation smarter by ensuring answers have the same amount of zeros at the end
     *
     * @return a random question of type ChoiceEstimation
     */

    public Question.ChoiceEstimation getRandomChoiceEstimation() {
        Activity activity = activityController.getRandomActivity();

        //adding the correct answer at index 0
        long correct = activity.getPowerConsumption();

        //ignore numbers that are too big for the question generation
        if(correct >= 2000000000) {
            return getRandomChoiceEstimation();
        }
        System.out.println(correct);

        //finding out how many zeroes the correct answer has at the end
        int zeroCount = zerosAtEnd(correct);
        System.out.println("zeros " + zeroCount);

        List<Long> answers = new ArrayList<>();
        answers.add(correct);

        //setting random upper and lower bounds used in calculating min and max values
        float lowerBound = -1L;
        while(lowerBound <= 0) {
            lowerBound = ThreadLocalRandom.current().nextLong(7L)/10F;
        }

        float upperBound = -1;
        while(upperBound <= 0) {
            upperBound = ThreadLocalRandom.current().nextLong(5L)/10F;
        }

        //adding two other answers with a maximum difference of lower/upper bound
        long min = Math.round(lowerBound * correct);
        long max = Math.round((upperBound + 1) * correct);

        //fix the bug where the correct answer is quite small
        if(correct <= 10) {
            while(answers.size() < 3) {
                long rand = random.nextInt(10);
                if(!answers.contains(rand)) {
                    answers.add(rand);
                }
            }
        }



        while (answers.size() < 3) {
            long randomNumber = ThreadLocalRandom.current().nextLong(min,max + 1);
            //for larger numbers make sure each answer has the same amount of zeros at end
            /*
            if(correct % 5F == 0 && zeroCount == 0) {
                char[] digits = String.valueOf(correct).toCharArray();
                int lastDigit = Integer.parseInt(String.valueOf(digits[digits.length - 1]));
                //take care of numbers ending in 5
                if(lastDigit == 5) {
                    int digitsAmount = String.valueOf(randomNumber).toCharArray().length;
                    while(Integer.parseInt(String.valueOf(String.valueOf(randomNumber).toCharArray()[digitsAmount - 1])) != 5 ||
                            Integer.parseInt(String.valueOf(String.valueOf(randomNumber).toCharArray()[digitsAmount - 1])) != 10) {
                        randomNumber++;
                    }
                }
                if(zeroCount > 0) {
                    for(int i = 0; i < zeroCount; i++) {
                        Math.round(randomNumber /= 10F);
                    }
                    if(zerosAtEnd(randomNumber) > 0) {
                        randomNumber += random.nextInt(10);
                    }
                    for(int i = 0; i < zeroCount; i++) {
                        randomNumber *= 10F;
                    }
                }
            }*/

            if (!answers.contains(randomNumber) && randomNumber > 0) {
                answers.add(randomNumber);
            }
        }
        return new Question.ChoiceEstimation(List.of(activity), answers);
    }


    /**
     * @param num number
     * @return amount of zeros at end of num
     */

    public int zerosAtEnd(Long num) {
        long correctToDiv = num;
        int zeroCount = 0;
        while(correctToDiv % 10L == 0) {
            correctToDiv /= 10L;
            zeroCount++;
        }
        return zeroCount;
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
        Question.MostNRGQuestion toRet = new Question.MostNRGQuestion(activities, activities.get(0), null);
        Collections.shuffle(toRet.getActivities());
        return toRet;
    }
}
