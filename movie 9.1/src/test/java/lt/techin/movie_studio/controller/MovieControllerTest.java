package lt.techin.movie_studio.controller;

import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.security.SecurityConfig;
import lt.techin.movie_studio.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = MovieController.class)
@Import(SecurityConfig.class)
public class MovieControllerTest {

  private static final Logger log = LoggerFactory.getLogger(MovieControllerTest.class);
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MovieService movieService;

  @Test
  @WithMockUser(authorities = "SCOPE_ROLE_USER")
  void getMovies_whenFindAll_thenReturnAllAnd200() throws Exception {
    Movie movie1 = new Movie("A test movie", "A test director",
            List.of(),
            List.of(new Actor(8, "John", "Johnson", 50), new Actor(9, "Peter", "Peterson", 50)));

    Movie movie2 = new Movie("A test movie II", "A test director II",
            List.of(),
            List.of(new Actor(10, "Freddy", "Freddysen", 50), new Actor(11, "Kregialnia", "Man", 50)));

    List<Movie> movies = List.of(movie1, movie2);

    given(movieService.findAllMovies()).willReturn(movies);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("[0].id").exists())
            .andExpect(jsonPath("[0].title").value("A test movie"))
            .andExpect(jsonPath("[0].director").value("A test director"))

            .andExpect(jsonPath("[0].screening").isArray())
            .andExpect(jsonPath("[0].screening", Matchers.hasSize(0)))

            .andExpect(jsonPath("[0].actors.id").value(8))
            .andExpect(jsonPath("[0].actors.name").value("John"))
            .andExpect(jsonPath("[0].actors.surname").value("Johnson"))
            .andExpect(jsonPath("[0].actors.age").value(50))

            .andExpect(jsonPath("[0].actors.id").value(9))
            .andExpect(jsonPath("[0].actors.name").value("Peter"))
            .andExpect(jsonPath("[0].actors.surname").value("Peterson"))
            .andExpect(jsonPath("[0].actors.age").value(50))

            .andExpect(jsonPath("[1].id").exists())
            .andExpect(jsonPath("[1].title").value("A test movie II"))
            .andExpect(jsonPath("[1].director").value("A test director II"))

            .andExpect(jsonPath("[1].screening").isArray())
            .andExpect(jsonPath("[1].screening", Matchers.hasSize(0)))

            .andExpect(jsonPath("[1].actors.id").value(10))
            .andExpect(jsonPath("[1].actors.name").value("Freddy"))
            .andExpect(jsonPath("[1].actors.surname").value("Freddysen"))
            .andExpect(jsonPath("[1].actors.age").value(50))

            .andExpect(jsonPath("[1].actors.id").value(11))
            .andExpect(jsonPath("[1].actors.name").value("Kregialnia"))
            .andExpect(jsonPath("[1].actors.surname").value("Man"))
            .andExpect(jsonPath("[1].actors.age").value(50));


    Mockito.verify(movieService, times(2)).findAllMovies();

  }

}
