package com.example.carRental.dto;

import com.example.carRental.model.Role;

import java.util.List;

public record UserDTO(long id, String username,
                      String password, List<Role> roles) {

}
