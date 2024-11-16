package com.biblioteca.dto;

import com.biblioteca.entities.UserType;

public record RegisterDTO(String name, String password, String email, String cpf, UserType type) {

}
