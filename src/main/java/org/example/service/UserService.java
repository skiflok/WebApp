package org.example.service;

import java.util.Optional;
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
}
