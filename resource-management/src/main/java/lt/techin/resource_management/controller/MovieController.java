package lt.techin.resource_management.controller;

import lt.techin.resource_management.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

  private final List<Movie> movies = new ArrayList<>(List.of(new Movie(1, "test movie", "test movie director"), new Movie(2, "test movie II", "test movie II director")));

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movies);
  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovie(@PathVariable int index) {
    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movies.get(index));
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    movies.add(movie);

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{index}").buildAndExpand(movies.size() - 1).toUri()).body(movie);
  }

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> getMoviesByTitle(@RequestParam String title) {
    List<Movie> foundMovies = movies.stream().filter(movie -> movie.getTitle().contains(title)).toList();

    if (foundMovies.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(foundMovies);
  }

  @PutMapping("/movies/{index}")
  public ResponseEntity<Movie> updateMovie(@PathVariable int index, @RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    if (index <= movies.size() - 1) {
      Movie movieFromDb = movies.get(index);

      movieFromDb.setTitle(movie.getTitle());
      movieFromDb.setDirector(movie.getDirector());
      movieFromDb.setId(movie.getId());

      return ResponseEntity.ok(movieFromDb);
    }
    movies.add(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{index}").buildAndExpand(movies.size() - 1).toUri()).body(movie);
  }
}