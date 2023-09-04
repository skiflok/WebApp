package org.example.service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.example.exception.UserIsAlreadyExistException;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    user.setActive(true);
//    user.setRoles(Collections.singleton(Role.USER));

    user.setActivationCode(UUID.randomUUID().toString());

    if (!user.getEmail().isEmpty()) {
      String text = String.format("Hello, %s\n" +
          "Welcome to WebApp. Please visit next link: %s%s",
          user.getUsername(),
          HOST,
          user.getActivationCode());
      mailSender.send(user.getEmail(), "Activation code", text);
    }

    userRepository.save(user);

  }
}
