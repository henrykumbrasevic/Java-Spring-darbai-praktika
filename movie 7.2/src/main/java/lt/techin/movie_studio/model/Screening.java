package lt.techin.movie_studio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotEmpty(message = "date field can't be empty")
  @Size(min = 2, max = 70, message = "theater name must be between 2-70 characters")
  private String theaterName;

  @NotNull(message = "can't be null")
  @FutureOrPresent(message = "date can't be in the past")
  private LocalDateTime screeningTime;


  public Screening(long id, String theaterName, LocalDateTime screeningTime) {
    this.theaterName = theaterName;
    this.screeningTime = screeningTime;
  }

  public Screening() {

  }

  public long getId() {
    return id;
  }

  public String getTheaterName() {
    return theaterName;
  }

  public void setTheaterName(String theaterName) {
    this.theaterName = theaterName;
  }

  public LocalDateTime getScreeningTime() {
    return screeningTime;
  }

  public void setScreeningTime(LocalDateTime screeningTime) {
    this.screeningTime = screeningTime;
  }
}
