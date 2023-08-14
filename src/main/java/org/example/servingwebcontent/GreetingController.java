package org.example.servingwebcontent;

import java.util.Map;
import org.example.model.Message;
import org.example.repositories.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

  private final MessageRepository messageRepository;

  public GreetingController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @GetMapping("/")
  public String greeting(Map<String, Object> model) {
    return "greeting";
  }

  @GetMapping("/main")
  public String main(Map<String, Object> model) {
    System.out.println("\n########  main #########\n");
    Iterable<Message> messages = messageRepository.findAll();
    model.put("messages", messages);
    return "main";
  }

  @PostMapping("/main")
  public String add(@RequestParam String text, @RequestParam String tag,
      Map<String, Object> model) {
    Iterable<Message> messages;
    if (text == null || text.isEmpty()) {
      messages = messageRepository.findAll();
      model.put("messages", messages);
      return "main";

    }
    messageRepository.save(new Message(text, tag));
    messages = messageRepository.findAll();
    model.put("messages", messages);
    return "main";
  }

  @PostMapping("filter")
  public String filter(@RequestParam String filter, Map<String, Object> model) {
    Iterable<Message> messages = null;
    if (filter != null && !filter.isEmpty()) {
      messages = messageRepository.findByTag(filter);
    } else {
      messages = messageRepository.findAll();
    }
    model.put("messages", messages);
    return "main";
  }

}