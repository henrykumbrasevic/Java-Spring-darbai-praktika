package lt.techin.resource_management.model;

public class Movie {

  private int id;
  private String title;
  private String director;

  public Movie() {
  }

  public Movie(int id, String title, String director) {
    this.id = id;
    this.title = title;
    this.director = director;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
