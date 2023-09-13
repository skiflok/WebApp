package org.example.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_TEST,
  ROLE_USER,
  ROLE_ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
