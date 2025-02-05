package lt.techin.movie_studio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.model.Screening;
import lt.techin.movie_studio.validation.Director;

import java.util.List;

public record MovieDTO(@NotNull(message = "title field can't be null")
                       long id,
                       @Size(min = 2, max = 100, message = "title field must have at least 2 characters but not more than 100. Also it can't be null")
                       String title,
                       @Director
                       String director, List<Screening> screenings,
                       @NotEmpty(message = "list can't be empty")
                       List<Actor> actors) {
}
