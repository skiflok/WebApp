package org.example.repositories;

import java.util.Optional;
import org.example.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUsername(String username);

}
