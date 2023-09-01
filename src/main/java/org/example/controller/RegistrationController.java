package org.example.controller;

import java.util.Collections;
import java.util.Optional;
import org.example.exception.UserIsAlreadyExistException;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class RegistrationController {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

  @Autowired
  private UserService userService;

  @GetMapping("/registration")
  public String registration() {
    return "/registration";
  }

  @PostMapping("/registration")
  public String addUser(User user, Model model) {

    try {
      userService.addUser(user);
    } catch (UserIsAlreadyExistException e) {
      model.addAttribute("messages", e.getMessage());
      logger.info(e.getMessage());
      return "/registration";
    }

    logger.info("User {} added", user.getUsername());
    return "redirect:/login";
  }

}
