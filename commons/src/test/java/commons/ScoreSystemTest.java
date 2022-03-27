package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreSystemTest {

    /**
     * Testing the "normal" score system
     */
    @Test
    void testNormalScore1() {
        int time = 69;
        assertEquals(310, ScoreSystem.calculateScore((long) time));
    }

    /**
     * Testing "normal" score system when time has run out
     */
    @Test
    void testNormalScore2() {
        int time = 100;
        assertEquals(0, ScoreSystem.calculateScore((long) time));
    }

    /**
     * Testing maximum score possible for "normal" score system
     */
    @Test
    void testNormalScore3() {
        int time = 0;
        assertEquals(1000, ScoreSystem.calculateScore((long) time));
    }

    /**
     * Testing the estimation score system, when the answer is more than double the correct value,
     * but still has the same number of digits
     */
    @Test
    void testEstimationScore1() {
        int time = 69;
        int correctAnswer = 100;
        int actualAnswer = 250;
        assertEquals(31, ScoreSystem.calculateScore((long)time, (long) actualAnswer, (long) correctAnswer));
    }

    /**
     * Testing the estimation score system, when the answer is more than the correct value, but less than double
     */
    @Test
    void testEstimationScore2() {
        int time = 69;
        int correctAnswer = 100;
        int actualAnswer = 150;
        assertEquals(77, ScoreSystem.calculateScore((long)time, (long) actualAnswer, (long) correctAnswer));
    }

    /**
     * Testing the estimation score system, when the answer is less than the correct value
     */
    @Test
    void testEstimationScore3() {
        int time = 69;
        int correctAnswer = 100;
        int actualAnswer = 50;
        assertEquals(77, ScoreSystem.calculateScore((long)time, (long) actualAnswer, (long) correctAnswer));
    }

    /**
     * Testing the estimation score system, when the answer is more than double the correct value,
     * and they don't have the same number of digits
     */
    @Test
    void testEstimationScore4() {
        int time = 69;
        int correctAnswer = 100;
        int actualAnswer = 4000;
        assertEquals(0, ScoreSystem.calculateScore((long)time, (long) actualAnswer, (long) correctAnswer));
    }

    /**
     * Testing the maximum score possible for estimation score system
     */
    @Test
    void testEstimationScore5() {
        int time = 0;
        int correctAnswer = 100;
        int actualAnswer = 100;
        assertEquals(1000, ScoreSystem.calculateScore((long)time, (long) actualAnswer, (long) correctAnswer));
    }

    /**
     * Testing the estimation score system when time has run out
     */
    @Test
    void testEstimationScore6() {
        int time = 100;
        int correctAnswer = 100;
        int actualAnswer = 50;
        assertEquals(0, ScoreSystem.calculateScore((long)time, (long) actualAnswer, (long) correctAnswer));
    }
}