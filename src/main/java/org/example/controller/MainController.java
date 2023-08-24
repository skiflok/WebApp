package org.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.example.model.Message;
import org.example.model.User;
import org.example.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

  private final MessageRepository messageRepository;

  public MainController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @Value("${upload.path}")
  String uploadPath;

  @GetMapping("/")
  public String greeting() {
    return "greeting";
  }

  @GetMapping("/main")
  public String main(
      @RequestParam(required = false, defaultValue = "") String filter,
      Model model) {
    System.out.println("\n########  main #########\n");
    Iterable<Message> messages = messageRepository.findAll();

    if (filter != null && !filter.isEmpty()) {
      messages = messageRepository.findByTag(filter);
    } else {
      messages = messageRepository.findAll();
    }

    model.addAttribute("messages", messages);
    model.addAttribute("filter", filter);
    return "main";
  }

  @PostMapping("add")
  public String add(
      @AuthenticationPrincipal User user,
      @RequestParam String text,
      @RequestParam String tag,
      @RequestParam("file") MultipartFile file,
      Model model) throws IOException {

    Iterable<Message> messages;
    if (text == null || text.isEmpty()) {
      messages = messageRepository.findAll();
      model.addAttribute("messages", messages);
      return "main";
    }

    Message msg = new Message(text, tag, user);

    if (file != null) {

      Path uploadDir = Paths.get(uploadPath);
      if (!Files.exists(uploadDir) && !Files.isDirectory(uploadDir)) {
        Files.createDirectories(uploadDir);
      }

      System.out.println("directory " + uploadDir);

      String uuidFile = UUID.randomUUID().toString();
      String resultFleName = uploadDir + "/" + uuidFile + "." + file.getOriginalFilename();
      System.out.println(resultFleName);

      file.transferTo(Files.createFile(Path.of(resultFleName)));

      msg.setFilename(resultFleName);
    }

    messageRepository.save(msg);
    messages = messageRepository.findAll();
    model.addAttribute("messages", messages);
    return "main";
  }
}