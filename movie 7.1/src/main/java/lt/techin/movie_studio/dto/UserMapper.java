package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.User;

import java.util.List;

public class UserMapper {


  public static List<UserDTO> toUserDTOList(List<User> users) {
    List<UserDTO> result = users.stream().map(user -> new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRoles())).toList();

    return result;
  }

  public static User toUser(UserDTO userDTO) {
    User user = new User();
    user.setPassword(userDTO.password());
    user.setUsername(userDTO.username());
    user.setRoles(userDTO.roles());
    return user;
  }

  public static UserDTO toUserDTO(User user) {
    return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
  }

  public static void updateUserFromDTO(User user, UserDTO userDTO) {
    user.setPassword(userDTO.password());
    user.setUsername(userDTO.username());
    user.setRoles(userDTO.roles());
  }
}
