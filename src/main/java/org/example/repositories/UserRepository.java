package org.example.repositories;

import java.util.List;
import java.util.Optional;
import org.example.model.Role;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByActivationCode(String code);
  List<User> findByRoles (Role role);

}
