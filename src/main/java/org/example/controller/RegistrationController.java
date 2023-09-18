package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.UserIsAlreadyExistException;
import org.example.model.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
  public String addUser(
      @RequestParam("password2") String passwordConfirm,
      @Valid User user,
      BindingResult bindingResult,
      Model model) {

    boolean isConfirmEmpty = passwordConfirm.isEmpty();
    logger.info("password not comfirm? {}",String.valueOf(isConfirmEmpty));

    if (isConfirmEmpty) {
      model.addAttribute("password2Error", "password confirmation cannot be empty");
    }

    if (user.getPassword() !=null && !user.getPassword().equals(passwordConfirm)) {
      model.addAttribute("passwordError", "Password mismatch");
//      return "/registration";
    }

    if (bindingResult.hasErrors() || isConfirmEmpty) {
      model.mergeAttributes(bindingResult.getModel());
      logger.info("bindingResult.hasErrors()");
      return "/registration";
    }


    try {
      userService.addUser(user);
      logger.info("user {} added", user);
      return "redirect:/login";
    } catch (UserIsAlreadyExistException e) {
      model.addAttribute("messages", e.getMessage());
      logger.info(e.getMessage());
      return "/registration";
    }

  }

  @GetMapping("/activate/{code}")
  public String activate(
      Model model,
      @PathVariable String code) {

    boolean isActivated = userService.activateUser(code);

    if (isActivated) {
      model.addAttribute("messages", "User successfully activated");
      model.addAttribute("messagesType", "success");
    } else {
      model.addAttribute("messages", "Activation code is not found");
      model.addAttribute("messagesType", "danger");
    }
    return "/login";
  }
}
