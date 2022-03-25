package server.Question;

import commons.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Handles HTTP requests to the path specified
 * Gets 20 questions from QuestionService
 */

@RestController
@RequestMapping(path = "api/questions")
public class QuestionController {

    /**
     * The service containing the logic for generating questions
     */

    private final QuestionService questionService;


    /**
     * Constructor where we inject the service layer into the controller
     *
     * @param questionService - the service used
     */

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    /**
     * Method for getting 20 questions
     */

    @GetMapping(path = "/next")
    public ResponseEntity<List<Question>> getRandomQuestions() {
        return ResponseEntity.ok(questionService.getQuestions());
    }

}
