package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.dto.EditUserDTO;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> listAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public String deleteUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isPresent()) {
            userRepository.deleteById(userId);
            return "Usuário deletado com sucesso!";
        } else {
            return "Usuário não encontrado!";
        }
    }

    public UserEntity updateUser(Long userId, EditUserDTO newUser) {
        UserEntity existingUser = findUserById(userId);

        existingUser.setUserName(newUser.name());
        existingUser.setUserCpf(newUser.cpf());

        if (newUser.password() != null && !newUser.password().isEmpty()) {
            String passEncoded = newUser.password();
            existingUser.setUserPassword(passEncoded);
        }

        existingUser.setUserEmail(newUser.email());
        existingUser.setUserType(newUser.type());
        return userRepository.save(existingUser);
    }
}
