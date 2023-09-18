package org.example.service;

import org.example.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  public void deleteAllMessage () {
    messageRepository.deleteAll();
  }

}
