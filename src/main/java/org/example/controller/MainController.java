package org.example.controller;

import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.example.model.Message;
import org.example.model.User;
import org.example.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

  private static final Logger logger = LoggerFactory.getLogger(MainController.class);

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
      @Valid Message message,
      BindingResult bindingResult,
      Model model,
      @RequestParam("file") MultipartFile file) throws IOException {

    message.setAuthor(user);

    if (bindingResult.hasErrors()) {
      model.mergeAttributes(bindingResult.getModel());
      model.addAttribute("message", message);
    } else {

      if (!file.isEmpty()) {
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir) && !Files.isDirectory(uploadDir)) {
          Files.createDirectories(uploadDir);
        }
        System.out.println("directory " + uploadDir);
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "." + file.getOriginalFilename();
        Path resultFilePath = Paths.get(uploadPath, fileName);
        file.transferTo(resultFilePath);
        message.setFilename(fileName);
      }
      messageRepository.save(message);
    }

    Iterable<Message> messages;
    messages = messageRepository.findAll();
    model.addAttribute("messages", messages);
    return "main";
  }

  @PostMapping("/deleteAll")
  public String deleteAll () {
    logger.info("deleteAll ()");
    messageRepository.deleteAll();
    return "redirect:/main";
  }
}