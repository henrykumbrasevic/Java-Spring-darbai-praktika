package lt.techin.movie_studio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String theaterName;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
  private Timestamp screeningTime;


  public Screening(long id, String theaterName, Timestamp screeningTime) {
    this.id = id;
    this.theaterName = theaterName;
    this.screeningTime = screeningTime;

  }

  public Screening() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTheaterName() {
    return theaterName;
  }

  public void setTheaterName(String theaterName) {
    this.theaterName = theaterName;
  }

  public Timestamp getScreeningTime() {
    return screeningTime;
  }

  public void setScreeningTime(Timestamp screeningTime) {
    this.screeningTime = screeningTime;
  }


}
