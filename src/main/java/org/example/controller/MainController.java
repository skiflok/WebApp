package org.example.controller;

import org.example.model.Message;
import org.example.repositories.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

  private final MessageRepository messageRepository;

  public MainController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @GetMapping("/")
  public String greeting() {
    return "greeting";
  }

  @GetMapping("/main")
  public String main(Model model) {
    System.out.println("\n########  main #########\n");
    Iterable<Message> messages = messageRepository.findAll();
    model.addAttribute("messages", messages);
    return "main";
  }

  @PostMapping("add")
  public String add(@RequestParam String text, @RequestParam String tag,
      Model model) {
    System.out.printf("\n########  add #########\n text %s tag %s", text, tag);
    Iterable<Message> messages;
    if (text == null || text.isEmpty()) {
      System.out.println("if (text == null || text.isEmpty()) ");
      messages = messageRepository.findAll();
      model.addAttribute("messages", messages);
      return "main";
    }
    messageRepository.save(new Message(text, tag));
    messages = messageRepository.findAll();
    model.addAttribute("messages", messages);
    System.out.println("add completed");
    return "main";
  }

  @PostMapping("filter")
  public String filter(@RequestParam String filter, Model model) {
    Iterable<Message> messages;
    if (filter != null && !filter.isEmpty()) {
      messages = messageRepository.findByTag(filter);
    } else {
      messages = messageRepository.findAll();
    }
    model.addAttribute("messages", messages);
    return "main";
  }

}