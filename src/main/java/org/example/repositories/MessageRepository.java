package org.example.repositories;

import org.example.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
