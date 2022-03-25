package commons;

public class ScoreSystem {
    /**
     * Placeholder method for counting points in "normal" (choice) questions:
     * Most Energy, ChoiceEstiamate, Matching
     *
     * @param time spent on a question
     * @return
     */
    public static int calculateScore(Long time) {
        return (int) ((100 - time) * 10);
    }

    /**
     * Placeholder method for counting points in AccurateEstimation questions
     *
     * @param time     spent on a question
     * @param distance to correct answer
     * @return
     */
    public static int calculateScore(Long time, Long distance) {
        return (int) ((100 - time) * 10 - distance * 10);
    }
}
