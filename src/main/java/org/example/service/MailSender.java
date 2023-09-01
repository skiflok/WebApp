package org.example.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
  private JavaMailSender mailSender;

  public void send(String emailTo, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom("");
    mailMessage.setFrom(emailTo);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);

    mailSender.send(mailMessage);
  }
}
