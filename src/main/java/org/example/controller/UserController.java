package org.example.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

  @PostMapping
  public String userSave(
      @RequestParam String username,
      @RequestParam("userId") User user,
      @RequestParam(value = "roles", defaultValue = "NONE") Set<String> formRoles)
  {

    user.setUsername(username);

    Set<String> roles = Arrays.stream(Role.values())
        .map(Role::name)
        .collect(Collectors.toSet());

    user.getRoles().clear();

    for (String value : formRoles) {
      if (roles.contains(value)) {
        user.getRoles().add(Role.valueOf(value));
      }
    }

    userRepository.save(user);

    return "redirect:/user";
  }

}
