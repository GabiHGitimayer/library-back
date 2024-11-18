package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.dto.DeleteUserResponseDTO;
import com.biblioteca.dto.EditUserDTO;
import com.biblioteca.dto.EditUserResponse;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> listAll() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditUserResponse> update(@PathVariable Long id, @RequestBody EditUserDTO user) {
        return ResponseEntity
                .ok(new EditUserResponse("Usuário alterado com sucesso!", userService.updateUser(id, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserResponseDTO> delete(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(new DeleteUserResponseDTO(message));
    }
}
