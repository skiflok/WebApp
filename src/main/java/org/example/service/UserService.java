package org.example.service;

import java.util.Collections;
import java.util.Optional;
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

  @Autowired
  private UserRepository userRepository;

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
    user.setRoles(Collections.singleton(Role.USER));
    userRepository.save(user);

  }
}
