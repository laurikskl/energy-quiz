package server.MultiChoice;

import commons.Activity;
import commons.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/questions")
public class MultiChoiceController {

  private final MultiChoiceService multiChoiceService;

  public MultiChoiceController(MultiChoiceService multiChoiceService){
    this.multiChoiceService = multiChoiceService;
  }

  /**
   * Uses the 2 methods in MultiChoiceService
   * @return A MultiChoice Question with a list of 3 random activities
   * and the correct answer
   */
  @GetMapping("/multichoice")
  public Question.MultiChoice getMultiChoice(){
    List<Activity> activities = multiChoiceService.getList();
    Activity correct = multiChoiceService.findCorrect(activities);
    return new Question.MultiChoice(activities, correct);
  }

}
