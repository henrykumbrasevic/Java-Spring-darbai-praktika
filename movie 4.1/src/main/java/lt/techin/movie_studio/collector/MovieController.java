package lt.techin.movie_studio.collector;

import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.service.MovieService;
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

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable long id) {
    Optional<Movie> foundMovie = movieService.findMovieById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(foundMovie.get());
  }

  @PostMapping("/movies")
  public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
    if (movie.getDirector().isEmpty() || movie.getTitle().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there can't be empty fields");
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(savedMovie);
  }


  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there can't be empty fields.");
    }
    if (movieService.existsMovieById(id)) {

      Movie movieFromDb = movieService.findMovieById(id).get();

      movieFromDb.setDirector(movie.getDirector());
      movieFromDb.setTitle(movie.getTitle());
      movieFromDb.setScreenings(movie.getScreenings());

      return ResponseEntity.ok(movieService.saveMovie(movieFromDb));
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/api/movies/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(movie);
  }

  @DeleteMapping("/movie/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (!movieService.existsMovieById(id)) {
      return ResponseEntity.notFound().build();
    }
    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }
}
