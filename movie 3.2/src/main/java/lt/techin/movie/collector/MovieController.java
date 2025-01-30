package lt.techin.movie.collector;

import lt.techin.movie.MovieApplication;
import lt.techin.movie.model.Movie;
import lt.techin.movie.service.MovieService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("movie/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable long id) {
    Optional<Movie> foundMovie = movieService.findMovieById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(foundMovie.get());
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(movie);
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not leave empty fields");
    }
    if (movieService.existsMovieById(id)) {
      Movie movieFromDb = movieService.findMovieById(id).get();

      movieFromDb.setTitle(movie.getTitle());
      movieFromDb.setDirector(movie.getDirector());

      return ResponseEntity.ok(movieService.saveMovie(movieFromDb));
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("api/movies/{id}")
            .buildAndExpand(savedMovie.getId())
            .toUri()).body(movie);
  }

  @DeleteMapping("movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.existsMovieById(id)) {
      return ResponseEntity.notFound().build();
    }

    movieService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }
}
