package lt.techin.movie_studio.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.movie_studio.model.Role;

import java.util.List;

public record UserDTO(long id, String username,
                      String password, List<Role> roles) {

}
