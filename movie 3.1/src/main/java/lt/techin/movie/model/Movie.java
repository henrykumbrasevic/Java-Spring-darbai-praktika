package lt.techin.movie.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String title;
  private String director;

  public Movie() {

  }

  public Movie(long id, String title, String director) {
    this.id = id;
    this.title = title;
    this.director = director;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }
}
