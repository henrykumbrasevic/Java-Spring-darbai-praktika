package lt.techin.movie.service;

import lt.techin.movie.model.Movie;
import lt.techin.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

  private MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }

  public Optional<Movie> findMovieById(long id) {
    return movieRepository.findById(id);
  }

  public Movie saveMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public boolean existsMovieById(long id) {
    return movieRepository.existsById(id);
  }

  public void deleteBookById(long id) {
    movieRepository.deleteById(id);
  }

  public boolean existsMovieByTitle(String title) {
    return movieRepository.existsByTitle(title);
  }

  public boolean existsMovieByDirector(String director) {
    return movieRepository.existsByDirector(director);
  }
}
