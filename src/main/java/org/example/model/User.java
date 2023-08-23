package org.example.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(unique = true) // Делаем username уникальным
  private String username;
  private String password;
  private boolean active;

  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive();
  }
}


/*
Эти методы - часть интерфейса org.springframework.security.core.userdetails.UserDetails, который определяет контракт для класса, представляющего информацию о пользователе для Spring Security. Здесь вы переопределяете эти методы, чтобы предоставить Spring Security информацию о состоянии аккаунта пользователя. Давайте разберемся подробнее:

isAccountNonExpired(): Этот метод возвращает true, если учетная запись пользователя не истекла по времени. В вашем случае, поскольку вы возвращаете true, это означает, что срок действия учетной записи не ограничен по времени.

isAccountNonLocked(): Этот метод возвращает true, если учетная запись пользователя не заблокирована. Снова, поскольку вы возвращаете true, это означает, что учетная запись не заблокирована.

isCredentialsNonExpired(): Этот метод возвращает true, если учетные данные пользователя (например, пароль) не истекли по времени. В вашем случае, поскольку вы возвращаете true, это означает, что учетные данные не ограничены по времени.

isEnabled(): Этот метод возвращает true, если учетная запись пользователя активна. Здесь вы возвращаете значение поля active из вашей сущности User, что означает, что активность учетной записи зависит от значения этого поля.

Таким образом, методы isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired() и isEnabled() позволяют Spring Security принимать решения о том, можно ли разрешить аутентификацию и авторизацию для данной учетной записи пользователя.
*/