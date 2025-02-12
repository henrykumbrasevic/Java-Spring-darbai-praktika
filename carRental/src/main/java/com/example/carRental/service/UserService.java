package com.example.carRental.service;

import com.example.carRental.model.User;
import com.example.carRental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> findUserById(long id) {
    return userRepository.findById(id);
  }

  public boolean existsUserById(long id) {
    return userRepository.existsById(id);
  }

  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }
}
