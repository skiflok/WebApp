package org.example.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/registration")
  public String registration () {
    return "/registration";
  }

  @PostMapping("/registration")
  public String addUser (User user, Model model) {
    System.out.printf("\n########  addUser #########\n text %s", user.getUsername());
    Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

    if (optionalUser.isPresent()) {
      System.out.printf("\n########  User exists #########\n text %s", user.getUsername());
      model.addAttribute("messages", "User exists");
      return "/registration";
    }

    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));
    userRepository.save(user);

    return "redirect:/login";
  }

}
