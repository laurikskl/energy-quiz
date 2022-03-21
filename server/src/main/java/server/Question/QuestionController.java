package server.Question;

import commons.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the API of questions.
 */
@RestController
@RequestMapping(path = "api/questions")
public class QuestionController {

  private final QuestionService questionService;

  /**
   * Constructor where we inject the service layer into the controller.
   *
   * @param questionService - the service used
   */
  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  /**
   * Method for getting the next question.
   */
  @GetMapping(path = "next")
  public ResponseEntity<Question> getRandomQuestion() {
    return ResponseEntity.ok(questionService.getRandomQuestion());
  }

}
