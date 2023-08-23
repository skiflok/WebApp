package org.example.config;

import java.util.Collections;
import org.example.model.Role;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
//public class AdminUserInitializer implements ApplicationRunner {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Override
//  public void run(ApplicationArguments args) {
//    User adminUser = new User();
//    adminUser.setUsername("admin");
////    adminUser.setPassword(passwordEncoder.encode("adminPassword"));
//    adminUser.setPassword("p");
//    adminUser.setActive(true);
//    adminUser.setRoles(Collections.singleton(Role.ADMIN));
//
//    userRepository.save(adminUser);
//  }
//}
