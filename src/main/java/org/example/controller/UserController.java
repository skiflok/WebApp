package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public String userList(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "/userList";
  }

  @GetMapping("{user}")
  public String userEditForm(
      @PathVariable User user,
      Model model) {
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    return "/userEdit";
  }

}
