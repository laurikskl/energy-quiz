package server.MultiChoice;

import commons.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questions")
public class MultiChoiceController {

  private final MultiChoiceService multiChoiceService;

  @Autowired
  public MultiChoiceController(MultiChoiceService multiChoiceService){
    this.multiChoiceService = multiChoiceService;
  }

  @GetMapping("/multichoice")
  public Question.MultiChoice getMultiChoice(){

  }

}
