package lt.techin.movie_studio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ActorDTO(long id,
                       @NotEmpty(message = "name field can't be empty")
                       @Size(min = 1, max = 50, message = "name length should be between 1-50 characters")
                       String name,
                       @NotEmpty(message = "name field can't be empty")
                       @Size(min = 1, max = 50, message = "name length should be between 1-50 characters")
                       String surname,
                       @NotNull(message = "age cannot be null")
                       @Positive
                       int age) {
}
