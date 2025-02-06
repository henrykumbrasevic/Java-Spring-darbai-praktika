package lt.techin.movie_studio.service;

import lt.techin.movie_studio.model.User;
import lt.techin.movie_studio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> finaAllUsers() {
    return userRepository.findAll();
  }
}
