package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {

  @Autowired
  private UserService userService;

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping
  public String userList(Model model) {
    model.addAttribute("users", userService.findAll());
    return "/userList";
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping("{user}")
  public String userEditForm(
      @PathVariable User user,
      Model model) {
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    return "/userEdit";
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PostMapping
  public String userSave(
      @RequestParam String username,
      @RequestParam("userId") User user,
      @RequestParam(value = "roles", defaultValue = "NONE") Set<String> formRoles)
  {

    userService.save(user, username, formRoles);
    return "redirect:/user";
  }

}
