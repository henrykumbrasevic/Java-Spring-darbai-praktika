package lt.techin.movie_studio.controller;

import lt.techin.movie_studio.dto.MovieMapper;
import lt.techin.movie_studio.dto.UserDTO;
import lt.techin.movie_studio.dto.UserMapper;
import lt.techin.movie_studio.model.User;
import lt.techin.movie_studio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getUsers() {
    return ResponseEntity.ok(UserMapper.toUserDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
    Optional<User> foundUser = userService.findUserById(id);

    if (foundUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(UserMapper.toUserDTO(foundUser.get()));
  }

  @PostMapping("/users")
  public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {

    User user = UserMapper.toUser(userDTO);
    user.setPassword(passwordEncoder.encode(userDTO.password()));

    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri())
            .body(savedUser);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
    if (userService.existsUserById(id)) {

      User userFromDb = userService.findUserById(id).get();
      UserMapper.updateUserFromDTO(userFromDb, userDTO);
      userService.saveUser(userFromDb);
      return ResponseEntity.ok(userDTO);
    }
    User savedUser = userService.saveUser(UserMapper.toUser(userDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/api/users/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri())
            .body(savedUser);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable long id) {
    if (!userService.existsUserById(id)) {
      return ResponseEntity.notFound().build();
    }
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}