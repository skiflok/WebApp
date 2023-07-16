package org.example.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Setter
  @NonNull
  private String text;
  @Setter
  @NonNull
  private String tag;
}
