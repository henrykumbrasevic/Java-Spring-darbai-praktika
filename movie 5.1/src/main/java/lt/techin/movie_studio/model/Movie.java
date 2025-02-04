package lt.techin.movie_studio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull(message = "title field can't be null")
  @Size(min = 2, max = 100, message = "title field must have at least 2 characters but not more than 100. Also it can't be null")
  private String title;

  //@NotNull(message = "director field can't be null")
  @Size(min = 2, max = 50, message = "director field must have at least 2 characters but not more than 50. Also it can't be null")
  // @Pattern(regexp = "^[A-Z][a-z]+$", message = "first letter must be capital following the lowercase letters. No numbers allowed")
  private String director;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "movie_id")
  private List<Screening> screenings;

  @ManyToMany
  @JoinTable(name = "movies_actors",
          joinColumns = @JoinColumn(name = "movie_id"),
          inverseJoinColumns = @JoinColumn(name = "actor_id"))

  @NotEmpty(message = "list can't be empty")
  // @NotNull
  private List<Actor> actors;

  public Movie() {

  }

  public Movie(long id, String title, String director, List<Screening> screenings, List<Actor> actors) {

    this.title = title;
    this.director = director;
    this.screenings = screenings;
    this.actors = actors;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Screening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screening> screenings) {
    this.screenings = screenings;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public List<Actor> getActors() {
    return actors;
  }

  public void setActors(List<Actor> actors) {
    this.actors = actors;
  }
}
