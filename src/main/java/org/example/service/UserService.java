package org.example.service;

import org.example.exception.UserIsAlreadyExistException;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

  private final String HOST = "http://localhost:8080/activate/";

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailNotificationService mailSender;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> userOptional = userRepository.findByUsername(username);
      if (userOptional.isPresent()) return userOptional.get();

      throw new UsernameNotFoundException("User " + username + "not found");
  }

  public void addUser(User user) {
    Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

    if (optionalUser.isPresent()) {
      throw new UserIsAlreadyExistException("User is already exist");
    }

//    user.setRoles(Collections.singleton(Role.USER));

    user.setActivationCode(UUID.randomUUID().toString());

    if (!user.getEmail().isEmpty()) {
      sendActivationCodeMessage(user);
    }

    userRepository.save(user);

  }

  public boolean activateUser(String code) {
    Optional<User> optionalUser = userRepository.findByActivationCode(code);

    if (optionalUser.isPresent()) {
      if (!optionalUser.get().isActive()) {
        optionalUser.get().setActive(true);
        userRepository.save(optionalUser.get());
      }
      return true;
    }
    return false;
  }

    public List<User> findAll() {
      return userRepository.findAll();
    }

  public void save(User user, String username, Set<String> formRoles) {
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
  }

  public void updateProfile(User user, String password, String email) {

    boolean isEmailChanged = (email != null && !email.equals(user.getEmail()));

    if (isEmailChanged) {
      user.setEmail(email);

      if (!email.isEmpty()) {
        user.setActivationCode(UUID.randomUUID().toString());
//        user.setActive(false);
      }
    }

    if (!password.isEmpty()) {
      user.setPassword(password);
    }

    userRepository.save(user);

    if (isEmailChanged) {
      sendActivationCodeMessage(user);
    }

  }

  private void sendActivationCodeMessage(User user) {
    String text = String.format("Hello, %s\n" +
                    "Welcome to WebApp. Please visit next link: %s%s",
            user.getUsername(),
            HOST,
            user.getActivationCode());
    mailSender.send(user.getEmail(), "Activation code", text);
  }


}
