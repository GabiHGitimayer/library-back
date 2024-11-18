package com.biblioteca.dto;

import com.biblioteca.entities.UserEntity;

public record EditUserResponse(String message, UserEntity user) {
}
