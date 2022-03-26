package commons;

public class ScoreSystem {
    /**
     * Method for counting points in "normal" (choice) questions:
     * Most Energy, ChoiceEstimate, Matching
     *
     * @param time spent on a question
     * @return
     */
    public static int calculateScore(Long time) {
        return (int) ((100 - time) * 10);
    }

    /**
     * Method for counting points in AccurateEstimation questions
     *
     * @param time     spent on a question
     * @param actualAnswer  answer to the question
     * @param correctAnswer correct answer
     * @return
     */
    public static int calculateScore(Long time, Long actualAnswer, Long correctAnswer) {
        //we calculate the score to reward players that are closer to the correct value,
        //by calculating what percentage of the correct answer is the actual answer
        //anything above 200% doesn't give points
        int percentage;
        percentage = (int) ((actualAnswer * 100)/correctAnswer);
        if (percentage > 100) percentage = 200 - percentage;
        if (percentage < 0) percentage = 0;

        //implementing a multiplier, so when the answer and correct value are both small numbers (max. 3 digits),
        //having more than 200% difference is ok
        int multiplier = 0;
        if (String.valueOf(correctAnswer).length() == String.valueOf(actualAnswer).length()
            && correctAnswer != actualAnswer)
            multiplier = 4 - String.valueOf(correctAnswer).length();
        if (multiplier < 0) multiplier = 0;
        
        if ((percentage * percentage + 1000 * multiplier < 10000) && percentage == 0)
            return (int) ((100 - time) * (1000 * multiplier))/1000;

        //we raise to the power of 2, so the closer you get, the more points you get
        return (int) ((100 - time) * (percentage * percentage))/1000;
    }
}
