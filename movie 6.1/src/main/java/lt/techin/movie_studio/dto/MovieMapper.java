package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Movie;

import java.util.List;

public class MovieMapper {

  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    List<MovieDTO> result = movies.stream().map(movie -> new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getScreenings(), movie.getActors())).toList();

    return result;
  }
}
