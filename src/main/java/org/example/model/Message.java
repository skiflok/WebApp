package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_SEQ")
  @SequenceGenerator(name = "message_SEQ", sequenceName = "message_seq", allocationSize = 1)
  private Long id;
  @Setter
  @NonNull
  @NotBlank(message = "please fill the message")
  @Length(max = 2048, message = "Message too long (more than 2 kB)")
  private String text;
  @Setter
  @NonNull
  @Length(max = 255, message = "Tag too long (more than 255)")
  private String tag;
  @Setter
  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User author;
  @Setter
  private String filename;

  public String getAuthorName() {
    return author != null ? author.getUsername() : "<none>";
  }

}
