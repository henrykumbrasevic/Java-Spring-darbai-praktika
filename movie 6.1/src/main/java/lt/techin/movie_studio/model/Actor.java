package lt.techin.movie_studio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "actors")
public class Actor {

  @Id

  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private long id;

  @NotEmpty(message = "name field can't be empty")
  @Size(min = 1, max = 50, message = "name length should be between 1-50 characters")
  private String name;

  @NotEmpty(message = "surname field can't be empty")
  @Size(min = 1, max = 50, message = "surname length should be between 1-50 characters")
  private String surname;

  @NotNull(message = "age cannot be null")
  private int age;

  public Actor() {

  }

  public Actor(long id, String name, String surname, int age) {
    this.name = name;
    this.surname = surname;
    this.age = age;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
