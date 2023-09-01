package org.example.exception;

public class UserIsAlreadyExistException extends RuntimeException {
  public UserIsAlreadyExistException() {
    super();
  }
  public UserIsAlreadyExistException(String message) {
    super(message);
  }

}
