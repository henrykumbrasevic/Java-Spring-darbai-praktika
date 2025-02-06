package lt.techin.movie_studio.controller;

import jakarta.validation.Valid;
import lt.techin.movie_studio.dto.MovieDTO;
import lt.techin.movie_studio.dto.MovieMapper;
import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<List<MovieDTO>> getMovies() {
    return ResponseEntity.ok(MovieMapper.toMovieDTOList(movieService.findAllMovies()));
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<MovieDTO> getMovie(@PathVariable long id) {
    Optional<Movie> foundMovie = movieService.findMovieById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(MovieMapper.toMovieDTO(foundMovie.get()));
  }

  @PostMapping("/movies")
  public ResponseEntity<?> addMovie(@Valid @RequestBody MovieDTO movieDTO) {

    Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(MovieMapper.toMovieDTO(savedMovie));
  }


  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @Valid @RequestBody MovieDTO movieDTO) {

    if (movieService.existsMovieById(id)) {

      Movie movieFromDb = movieService.findMovieById(id).get();

//      movieFromDb.setDirector(movie.getDirector());
//      movieFromDb.setTitle(movie.getTitle());
//      movieFromDb.setScreenings(movie.getScreenings());
//      movieFromDb.setActors(movie.getActors());

      MovieMapper.updateMovieFromDTO(movieFromDb, movieDTO);
      movieService.saveMovie(movieFromDb);

      return ResponseEntity.ok(movieDTO);
    }

    Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/api/movies/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(movieDTO);
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
