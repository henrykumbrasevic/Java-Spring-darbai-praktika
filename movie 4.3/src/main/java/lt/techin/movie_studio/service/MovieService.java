package lt.techin.movie_studio.service;

import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

  public boolean existsMovieById(long id) {
    return movieRepository.existsById(id);
  }

  public Movie saveMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public void deleteMovieById(long id) {
    movieRepository.deleteById(id);
  }

  public Page<Movie> findAllMoviesPage(int page, int size, String sort) {
    if (sort == null) {
      Pageable pageable = PageRequest.of(page, size);
      return movieRepository.findAll(pageable);
    }

    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    return movieRepository.findAll(pageable);
  }
}
