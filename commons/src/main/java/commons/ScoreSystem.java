package commons;

public class ScoreSystem {
    /**
     * Placeholder method for counting points in "normal" (choice) questions:
     * Most Energy, ChoiceEstiamate, Matching
     *
     * @param time spent on a question
     * @return
     */
    public static Long calculateScore(Long time) {
        return (100 - time) * 100;
    }

    /**
     * Placeholder method for counting points in AccurateEstimation questions
     *
     * @param time     spent on a question
     * @param distance to correct answer
     * @return
     */
    public static Long calculateScore(Long time, Long distance) {
        return (100 - time) * 100 - distance * 10;
    }
}
