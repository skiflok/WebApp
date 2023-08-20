package org.example.repositories;

import java.util.List;
import org.example.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
  List<Message> findByTag(String tag);

}
